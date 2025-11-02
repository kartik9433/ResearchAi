import React, { useState } from "react";
import "./Search.css";

export default function Search() {
  const [input, setInput] = useState("");
  const [sentMessage, setSentMessage] = useState("");
  const [botReply, setBotReply] = useState("");

  const handleSend = async () => {
    if (!input.trim()) return;

    setSentMessage(input);
    setInput("");

    try {
      const backendurl = import.meta.env.VITE_BACKEND_URL;
      const res = await fetch(`${backendurl}/chatbot/ask`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ userMessage: input }),
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
        {sentMessage && (
          <div className="message user-msg">You: {sentMessage}</div>
        )}
        {botReply && (
          <div
            className="message bot-msg"
            dangerouslySetInnerHTML={{ __html: botReply.replace(/\n/g, "<br>") }}
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
        <button onClick={handleSend}>Send</button>
      </div>
    </div>
  );
}
