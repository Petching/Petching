import React, { useState, useEffect, useRef } from 'react';
import io, { Socket } from 'socket.io-client';

type ChatEvent = {
  message: string;
  room: string;
};

const ChatComponent: React.FC = () => {
  const [message, setMessage] = useState<string>('');
  const [messages, setMessages] = useState<{ room: string; message: string }[]>(
    [],
  );

  const socketRef = useRef<Socket | null>(null);
  const [room, setRoom] = useState<string>('defaultRoom');
  const [inputValue, setInputValue] = useState<string>('');
  const filteredMessages = messages.filter(msg => msg.room === room);
  useEffect(() => {
    socketRef.current = io('http://localhost:4000');

    socketRef.current.emit('joinRoom', room);

    socketRef.current?.on('chat', (data: ChatEvent) => {
      setMessages(messages => [...messages, data]);
    });

    return () => {
      socketRef.current?.disconnect();
    };
  }, [room]);

  const joinRoom = () => {
    if (socketRef.current && inputValue) {
      setRoom(inputValue);
      socketRef.current.emit('joinRoom', inputValue);
    }
  };

  const sendMessage = (e: React.FormEvent) => {
    e.preventDefault();

    if (socketRef.current) {
      socketRef.current.emit('chat', {
        message,
        room,
      });
      setMessage('');
    }
  };

  return (
    <div className="flex flex-col w-full h-screen p-4 md:p-8">
      <h2 className="text-xl mb-2 md:text-2xl">Current room: {room}</h2>
      <form className="mb-4">
        <input
          value={inputValue}
          onChange={e => setInputValue(e.target.value)}
          className="mb-3 p-2 rounded-md border w-full md:w-1/2 md:text-lg"
          placeholder="Enter room"
        />
        <button
          type="button"
          onClick={joinRoom}
          className="w-full bg-green-500 text-white p-2 rounded-md hover:bg-green-600 md:w-1/2 md:text-lg"
        >
          Join Room
        </button>
      </form>
      <div className="flex-grow overflow-auto mb-4 p-3 bg-white rounded-md">
        {filteredMessages.map((message, i) => (
          <div
            key={i}
            className="mb-3 p-2 rounded-md bg-gray-100 text-black flex items-center justify-start"
          >
            <div className="rounded-full h-8 w-8 bg-blue-500 mr-3 md:h-10 md:w-10"></div>
            <p className="text-sm md:text-base">{message.message}</p>
          </div>
        ))}
      </div>
      <form onSubmit={sendMessage} className="flex">
        <input
          id="input"
          type="text"
          value={message}
          onChange={e => setMessage(e.target.value)}
          className="flex-grow border rounded-l-md p-2 focus:outline-none focus:ring-2 focus:ring-green-600 md:text-lg"
        />
        <button
          id="send-button"
          type="submit"
          className="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded-r-md md:text-lg"
        >
          Send
        </button>
      </form>
    </div>
  );
};

export default ChatComponent;
