const chatBox = document.getElementById("chat-box");
const userInput = document.getElementById("user-input");
const sendBtn = document.getElementById("send-btn");

const user_id = "user123"; // could be dynamic if needed

function addMessage(sender, text) {
  const msg = document.createElement("div");
  msg.classList.add("message", sender);
  msg.innerText = text;
  chatBox.appendChild(msg);
  chatBox.scrollTop = chatBox.scrollHeight;
}

// send message to backend
async function sendMessage() {
  const message = userInput.value.trim();
  if (!message) return;

  addMessage("user", message);
  userInput.value = "";

  try {
    const response = await fetch("http://127.0.0.1:8000/chat/symptom", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ user_id, message })
    });

    const data = await response.json();
    addMessage("bot", data.reply);
  } catch (error) {
    addMessage("bot", "⚠️ Error: Unable to connect to the server.");
  }
}

sendBtn.addEventListener("click", sendMessage);
userInput.addEventListener("keypress", (e) => {
  if (e.key === "Enter") sendMessage();
});

// start the conversation
addMessage("bot", "Hello! Let's predict your diabetes risk. Type 'hi' to begin.");
