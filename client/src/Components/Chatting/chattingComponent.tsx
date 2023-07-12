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
    // Connect to the server
    socketRef.current = io('http://localhost:4000');

    // Listen for 'chat' event from server
    socketRef.current?.on('chat', (data: ChatEvent) => {
      console.log(data);
      setMessages(messages => [...messages, data.message]);
    });

    // Cleanup
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
    <div>
      <div id="messages">
        {messages.map((message, i) => (
          <p key={i}>{message}</p>
        ))}
      </div>
      <form onSubmit={sendMessage}>
        <input
          id="input"
          type="text"
          value={message}
          onChange={e => setMessage(e.target.value)}
        />
        <button id="send-button" type="submit">
          Send
        </button>
      </form>
    </div>
  );
};

export default ChatComponent;
