import React, { useState, useEffect } from "react";
import "./Search.css";

export default function Search() {
  const [input, setInput] = useState("");
  const [sentMessage, setSentMessage] = useState("");
  const [botReply, setBotReply] = useState("");
  const [listening, setListening] = useState(false);
  const [recognition, setRecognition] = useState(null);

  useEffect(() => {
    const SpeechRecognition =
      window.SpeechRecognition || window.webkitSpeechRecognition;

    if (!SpeechRecognition) {
      alert("❌ Speech Recognition not supported in this browser!");
      return;
    }

    const recog = new SpeechRecognition();
    recog.continuous = true; // allow longer input
    recog.lang = "en-US";
    recog.interimResults = false;

    recog.onresult = (event) => {
      const transcript = event.results[event.results.length - 1][0].transcript;
      console.log("🎤 You said:", transcript);
      setInput(transcript);
      handleSend(transcript); // send automatically
    };

    recog.onerror = (event) => {
      console.error("Speech recognition error:", event.error);
      setListening(false);
    };

    recog.onend = () => {
      console.log("Speech recognition ended");
      setListening(false);
    };

    setRecognition(recog);
  }, []);

  // 🎤 Mic Button Logic
  const handleMic = () => {
    if (!recognition) return;

    if (!listening) {
      setListening(true);
      recognition.start();
      console.log("🎙️ Listening...");
    } else {
      recognition.stop();
      setListening(false);
      console.log("🛑 Stopped listening");
    }
  };

  // 💬 Send message to backend
  const handleSend = async (userMessage = input) => {
    if (!userMessage.trim()) return;

    setSentMessage(userMessage);
    setInput("");

    try {
      const backendurl = import.meta.env.VITE_BACKEND_URL;
      const res = await fetch(`${backendurl}/chatbot/ask`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userMessage }),
      });

      const data = await res.text();
      setBotReply(data);
    } catch (error) {
      console.error("Error fetching response:", error);
      setBotReply("⚠️ Error connecting to chatbot server.");
    }
  };

  return (
    <div className="chat-container">
      <div className="chat-box">
        {sentMessage && <div className="message user-msg">You: {sentMessage}</div>}
        {botReply && (
          <div
            className="message bot-msg"
            dangerouslySetInnerHTML={{
              __html: botReply.replace(/\n/g, "<br>"),
            }}
          />
        )}
      </div>

      <div className="search-bar">
        <input
          type="text"
          placeholder="Ask anything..."
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleSend()}
        />

        {/* 🎤 Mic Button */}
        <button
          onClick={handleMic}
          style={{
            backgroundColor: listening ? "red" : "#007bff",
            color: "white",
            border: "none",
            borderRadius: "50%",
            width: "40px",
            height: "40px",
            fontSize: "18px",
            cursor: "pointer",
            display:"flex",
            alignItems:"center",
            justifyContent:"center"
          }}
        >
          {listening ? "🛑" : "🎤"}
        </button>

        <button onClick={() => handleSend(input)}>Send</button>
      </div>
    </div>
  );
}
