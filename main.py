from fastapi import FastAPI, Request
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
import joblib
import pandas as pd
import numpy as np

# ==============================================================
# 🚀 Initialize FastAPI
# ==============================================================
app = FastAPI(title="Unified Diabetes Chatbot API")

# ✅ Enable CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Allow all origins or specify frontend domain
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# ==============================================================
# 🧠 Load Models
# ==============================================================
lab_model = joblib.load("diabetes_model.pkl")
scaler = joblib.load("scaler.pkl")
symptom_model = joblib.load("symptom_diabetes_model.pkl")

# ==============================================================
# 💾 Store User Sessions
# ==============================================================
user_sessions = {}

# ==============================================================
# ⚗️ LAB-BASED CHATBOT CONFIG
# ==============================================================
lab_questions = [
    "How many pregnancies?",
    "What is your glucose level?",
    "What is your blood pressure?",
    "What is your skin thickness?",
    "What is your insulin level?",
    "What is your BMI?",
    "What is your diabetes pedigree function?",
    "What is your age?"
]

lab_keys = [
    "pregnancies", "glucose", "blood_pressure", "skin_thickness",
    "insulin", "bmi", "diabetes_pedigree", "age"
]

# ==============================================================
# 💬 SYMPTOM-BASED CHATBOT CONFIG
# ==============================================================
symptom_questions = [
    "What is your age?",
    "What is your gender? (Male/Female)",
    "Do you have frequent urination (polyuria)? (Yes/No)",
    "Do you feel excessive thirst (polydipsia)? (Yes/No)",
    "Have you experienced sudden weight loss? (Yes/No)",
    "Do you often feel weak or fatigued? (Yes/No)",
    "Do you feel excessive hunger (polyphagia)? (Yes/No)",
    "Do you have genital thrush? (Yes/No)",
    "Do you have blurred vision? (Yes/No)",
    "Do you experience itching? (Yes/No)",
    "Do you feel irritable? (Yes/No)",
    "Do you have delayed healing of wounds? (Yes/No)",
    "Do you have partial paralysis or weakness (paresis)? (Yes/No)",
    "Do you have muscle stiffness? (Yes/No)",
    "Do you have hair loss (alopecia)? (Yes/No)",
    "Are you obese? (Yes/No)"
]

symptom_keys = [
    "age", "gender", "polyuria", "polydipsia", "sudden_weight_loss",
    "weakness", "polyphagia", "genital_thrush", "visual_blurring",
    "itching", "irritability", "delayed_healing", "partial_paresis",
    "muscle_stiffness", "alopecia", "obesity"
]

# ==============================================================
# 🧩 LAB CHATBOT ROUTE
# ==============================================================
@app.post("/chat/lab")
async def chat_lab(request: Request):
    user_input = await request.json()
    user_id = f"lab_{user_input.get('user_id', 'default')}"
    message = user_input.get("message", "").lower()

    if user_id not in user_sessions:
        user_sessions[user_id] = {"step": 0, "data": {}}

    session = user_sessions[user_id]
    step = session["step"]

    # Save previous response
    if step > 0:
        key = lab_keys[step - 1]
        try:
            session["data"][key] = float(message)
        except ValueError:
            return JSONResponse({"reply": "Please enter a valid number."})

    # Ask next question
    if step < len(lab_questions):
        next_q = lab_questions[step]
        session["step"] += 1
        return JSONResponse({"reply": next_q})

    # All inputs collected → Predict
    data = session["data"]
    user_sessions[user_id] = {"step": 0, "data": {}}  # reset

    X = np.array([[data[k] for k in lab_keys]])
    X_scaled = scaler.transform(X)
    prediction = lab_model.predict(X_scaled)[0]

    result = "Diabetic (Positive)" if prediction == 1 else "Non-Diabetic (Negative)"
    return JSONResponse({
        "reply": f"✅ Based on your lab results, you are predicted to be: **{result}**.",
        "data": data,
        "prediction": int(prediction)
    })

# ==============================================================
# 🩺 SYMPTOM CHATBOT ROUTE
# ==============================================================
@app.post("/chat/symptom")
async def chat_symptom(request: Request):
    user_input = await request.json()
    user_id = f"symptom_{user_input.get('user_id', 'default')}"
    message = user_input.get("message", "").strip()

    if user_id not in user_sessions:
        user_sessions[user_id] = {"step": 0, "data": {}}

    session = user_sessions[user_id]
    step = session["step"]

    # Save previous response
    if step > 0:
        key = symptom_keys[step - 1]
        value = message.lower()

        if key == "age":
            try:
                session["data"][key] = int(value)
            except ValueError:
                return JSONResponse({"reply": "Please enter a valid age (number)."})
        elif key == "gender":
            if value not in ["male", "female"]:
                return JSONResponse({"reply": "Please answer 'Male' or 'Female'."})
            session["data"][key] = value.capitalize()
        else:
            if value not in ["yes", "no"]:
                return JSONResponse({"reply": "Please answer with 'Yes' or 'No'."})
            session["data"][key] = value.capitalize()

    # Ask next question
    if step < len(symptom_questions):
        next_q = symptom_questions[step]
        session["step"] += 1
        return JSONResponse({"reply": next_q})

    # All answers collected → Predict
    data = session["data"]
    user_sessions[user_id] = {"step": 0, "data": {}}

    df = pd.DataFrame([data])
    mapping = {"Yes": 1, "No": 0, "Male": 1, "Female": 0}
    df = df.replace(mapping)

    prediction = symptom_model.predict(df)[0]
    result = "Diabetic (Positive)" if prediction == 1 else "Non-Diabetic (Negative)"

    return JSONResponse({
        "reply": f"✅ Based on your symptoms, you are predicted to be: **{result}**.",
        "data": data,
        "prediction": int(prediction)
    })

# ==============================================================
# 🏠 Root
# ==============================================================
@app.get("/")
def home():
    return {
        "message": "🤖 Unified Diabetes Chatbot API is running!",
        "routes": {
            "Lab Test Chatbot": "/chat/lab",
            "Symptom-based Chatbot": "/chat/symptom"
        }
    }
