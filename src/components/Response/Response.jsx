import React from "react";
import "./Response.css"

function Response({ reply }) {
  return (
    <div className="message bot-message">
      <p>{reply}</p>
    </div>
  );
}

export default Response;
