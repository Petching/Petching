import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useState, useRef, useEffect } from 'react';

const ChatComponent: React.FC = () => {
  const [room, setRoom] = useState('');
  const inputRef = useRef(null);

  useEffect(() => {
    const sock = new SockJS('https://petching.net/ws/chat');
    const stomp = Stomp.over(sock);

    stomp.connect({}, () => {
      console.log('서버와 연결!');
    });
  });

  return (
    <div className="flex flex-col w-full h-screen p-4 md:p-8">
      <h2 className="text-xl mb-2 md:text-2xl">Current room:</h2>
      <form className="mb-4">
        <input
          className="mb-3 p-2 rounded-md border w-full md:w-1/2 md:text-lg"
          placeholder="Enter room"
        />
        <button
          type="button"
          className="w-full bg-[#99DDCC] text-white p-2 rounded-md hover:bg-[#79C3B1] md:w-1/2 md:text-lg"
        >
          Join Room
        </button>
      </form>
      <div className="flex-grow overflow-auto mb-4 p-3 bg-white rounded-md"></div>
      <form className="flex">
        <input
          id="input"
          type="text"
          className="flex-grow border rounded-l-md p-2 focus:outline-none focus:ring-1 md:text-lg"
        />
        <button
          id="send-button"
          type="submit"
          className="bg-[#99DDCC] hover:bg-[#79C3B1] text-white py-2 px-4 rounded-r-md md:text-lg"
        >
          Send
        </button>
      </form>
    </div>
  );
};

export default ChatComponent;
