import React from "react";
import ChattingComponent from "../Components/chattingComponent";
const Chatting = () => {
  const roomId = "room1";
  return (
    <div className="h-screen flex items-center justify-center bg-gray-100">
      <ChattingComponent roomId={roomId} />
    </div>
  );
};

export default Chatting;
