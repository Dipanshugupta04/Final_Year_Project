# ===============================================================
# 🧠 Combined Diabetes Model Trainer
# ===============================================================
# Trains and saves:
#  1. Lab-Test–Based Model (Logistic Regression)
#  2. Symptom-Based Model (Logistic Regression)
# ===============================================================

import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler, LabelEncoder
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, classification_report
import joblib

# ===============================================================
# 🧪 PART 1: LAB-TEST–BASED MODEL
# ===============================================================
print("===============================================================")
print("🧪 TRAINING LAB-BASED DIABETES MODEL")
print("===============================================================")

# 1️⃣ Load Dataset
lab_data = pd.read_csv("diabetes.csv")
print(f"✅ Loaded lab-based dataset: {lab_data.shape[0]} records")

# 2️⃣ Prepare Features and Labels
X_lab = lab_data.drop("Outcome", axis=1)
y_lab = lab_data["Outcome"]

# 3️⃣ Split into Train/Test
X_train_lab, X_test_lab, y_train_lab, y_test_lab = train_test_split(
    X_lab, y_lab, test_size=0.2, random_state=42
)

# 4️⃣ Scale Features
scaler_lab = StandardScaler()
X_train_lab_scaled = scaler_lab.fit_transform(X_train_lab)
X_test_lab_scaled = scaler_lab.transform(X_test_lab)

# 5️⃣ Train Model
model_lab = LogisticRegression(max_iter=1000)
model_lab.fit(X_train_lab_scaled, y_train_lab)

# 6️⃣ Evaluate
y_pred_lab = model_lab.predict(X_test_lab_scaled)
acc_lab = accuracy_score(y_test_lab, y_pred_lab)

print(f"🎯 Lab Model Accuracy: {acc_lab * 100:.2f}%")
print("---------------------------------------------------------------")
print(classification_report(y_test_lab, y_pred_lab))

# 7️⃣ Save Model and Scaler
joblib.dump(model_lab, "diabetes_model.pkl")
joblib.dump(scaler_lab, "scaler.pkl")
print("💾 Saved: diabetes_model.pkl, scaler.pkl")

# 8️⃣ Example Prediction
example_lab = {
    "Pregnancies": 6,
    "Glucose": 148,
    "BloodPressure": 72,
    "SkinThickness": 35,
    "Insulin": 0,
    "BMI": 33.6,
    "DiabetesPedigreeFunction": 0.627,
    "Age": 50
}
example_lab_df = pd.DataFrame([example_lab])
example_lab_scaled = scaler_lab.transform(example_lab_df)
prediction_lab = model_lab.predict(example_lab_scaled)[0]
result_lab = "Diabetic (Positive)" if prediction_lab == 1 else "Non-Diabetic (Negative)"
print(f"🧍 Example Lab Prediction: {result_lab}")

# ===============================================================
# 💬 PART 2: SYMPTOM-BASED MODEL
# ===============================================================
print("\n===============================================================")
print("💬 TRAINING SYMPTOM-BASED DIABETES MODEL")
print("===============================================================")

# 1️⃣ Load Dataset
symptom_data = pd.read_csv("diabetes_symptom_data.csv")

# Clean column names
symptom_data.columns = symptom_data.columns.str.strip().str.lower().str.replace(" ", "_")

print(f"✅ Loaded symptom-based dataset: {symptom_data.shape[0]} records")

# 2️⃣ Encode categorical values
le = LabelEncoder()
for col in symptom_data.columns:
    symptom_data[col] = le.fit_transform(symptom_data[col])

# 3️⃣ Split Data
X_sym = symptom_data.drop("class", axis=1)
y_sym = symptom_data["class"]
X_train_sym, X_test_sym, y_train_sym, y_test_sym = train_test_split(
    X_sym, y_sym, test_size=0.2, random_state=42
)

# 4️⃣ Train Model
model_sym = LogisticRegression(max_iter=1000)
model_sym.fit(X_train_sym, y_train_sym)

# 5️⃣ Evaluate
y_pred_sym = model_sym.predict(X_test_sym)
acc_sym = accuracy_score(y_test_sym, y_pred_sym)

print(f"🎯 Symptom Model Accuracy: {acc_sym * 100:.2f}%")
print("---------------------------------------------------------------")
print(classification_report(y_test_sym, y_pred_sym))

# 6️⃣ Save Model
joblib.dump(model_sym, "symptom_diabetes_model.pkl")
print("💾 Saved: symptom_diabetes_model.pkl")

# 7️⃣ Example Prediction
example_sym = {
    "age": 45,
    "gender": "Male",
    "polyuria": "Yes",
    "polydipsia": "Yes",
    "sudden_weight_loss": "No",
    "weakness": "Yes",
    "polyphagia": "No",
    "genital_thrush": "No",
    "visual_blurring": "Yes",
    "itching": "No",
    "irritability": "No",
    "delayed_healing": "Yes",
    "partial_paresis": "No",
    "muscle_stiffness": "No",
    "alopecia": "No",
    "obesity": "Yes"
}

example_sym_df = pd.DataFrame([example_sym])
for col in example_sym_df.columns:
    example_sym_df[col] = le.fit_transform(example_sym_df[col])

prediction_sym = model_sym.predict(example_sym_df)[0]
result_sym = "Diabetic (Positive)" if prediction_sym == 1 else "Non-Diabetic (Negative)"
print(f"🧍 Example Symptom Prediction: {result_sym}")

print("\n✅✅ All models trained and saved successfully!")
