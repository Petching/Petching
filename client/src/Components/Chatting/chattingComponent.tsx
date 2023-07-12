import React, { useState, useEffect, useRef } from 'react';
import io, { Socket } from 'socket.io-client';

type ChatEvent = {
  message: string;
};

const ChatComponent: React.FC = () => {
  const [message, setMessage] = useState<string>('');
  const [messages, setMessages] = useState<string[]>([]);
  const socketRef = useRef<Socket | null>(null);

  useEffect(() => {
    socketRef.current = io('http://localhost:4000');

    socketRef.current?.on('chat', (data: ChatEvent) => {
      console.log(data);
      setMessages(messages => [...messages, data.message]);
    });

    return () => {
      socketRef.current?.disconnect();
    };
  }, []);

  const sendMessage = (e: React.FormEvent) => {
    e.preventDefault();
    console.log(message);

    socketRef.current?.emit('chat', {
      message,
    });

    setMessage('');
  };

  return (
    <div className="flex flex-col ">
      <div id="messages" className="overflow-auto mb-4 p-3 flex-grow">
        {messages.map((message, i) => (
          <p key={i} className="mb-2 text-sm border p-2 rounded-lg bg-gray-100">
            {message}
          </p>
        ))}
      </div>
      <form onSubmit={sendMessage} className="flex">
        <input
          id="input"
          type="text"
          value={message}
          onChange={e => setMessage(e.target.value)}
          className="flex-grow border rounded-l-md p-2 focus:outline-none focus:ring-2 focus:ring-green-600"
        />
        <button
          id="send-button"
          type="submit"
          className="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded-r-md"
        >
          Send
        </button>
      </form>
    </div>
  );
};

export default ChatComponent;
