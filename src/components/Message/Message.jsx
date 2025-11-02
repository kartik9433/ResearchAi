import React from 'react'
import "./Message.css"

function Message({input}) {
  return (
    <div>
        <span style={{border:"solid black 2px",color:"white"}}>{input}</span>
    </div>
  )
}

export default Message
