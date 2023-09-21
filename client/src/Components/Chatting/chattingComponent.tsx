/* eslint-disable */
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { useState, useRef, useEffect, useId } from 'react';
import { Axios } from '../../API/api';
import { getUserIdFromToken } from '../../Util/getUserIdFromToken';
import axios from 'axios';

const ChatComponent: React.FC = () => {
  const [room, setRoom] = useState('');
  const inputRef = useRef<HTMLInputElement>(null);

  const sock = new WebSocket('wss://server.petching.net/ws/chat');
  const stomp = Stomp.over(sock);
  const Token = localStorage.getItem('ACCESS_TOKEN') || '';
  const userId = String(getUserIdFromToken(true, Token));
  const [roomId, setRoomId] = useState('');
  console.log(userId);

  //방을 생성하는 postAPI
  //roomId값도 리턴함
  const postChat = async (userId: string) => {
    try {
      const response = await axios.post(
        'https://server.petching.net/chats',
        { userId: userId },
        {
          headers: {
            Authorization: Token,
          },
        },
      );
      console.log(response);
      const headerResponse = response.headers.location;
      const roomId = headerResponse.split('/').pop();
      setRoomId(roomId);
      console.log(roomId);
    } catch (error) {
      console.error('방 생성 오류');
    }
  };

  // const subscribedUser = (subscribedUserId: string) => {
  //   stomp.subscribe(`/sub/chats/messages/${userId}`, message => {
  //     console.log('상대방에게 받은 메세지', message.body);
  //   });
  // };

  useEffect(() => {
    postChat(userId);

    stomp.connect({}, () => {
      console.log('서버와 연결!');
    });

    return () => {
      stomp.disconnect(() => {
        console.log('서버 연결 해제');
      });
    };
  }, []);

  const joinRoom = () => {
    stomp.subscribe(`/room/${room}`, message => {
      console.log(message.body);
    });
  };

  const sendMessage = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (inputRef.current) {
      const message = inputRef.current.value;
      stomp.send(`/room/${room}`, {}, message);
      inputRef.current.value = '';
    }
  };

  return (
    <div className="flex flex-col w-full h-screen p-4 md:p-8">
      <h2 className="text-xl mb-2 md:text-2xl">Current room:{roomId}</h2>
      <form className="mb-4" onSubmit={joinRoom}>
        {/* <input
          className="mb-3 p-2 rounded-md border w-full md:w-1/2 md:text-lg"
          placeholder="Enter room"
          value={room}
          onChange={e => setRoom(e.target.value)}
        />
        <button
          type="submit"
          className="w-full bg-[#99DDCC] text-white p-2 rounded-md hover:bg-[#79C3B1] md:w-1/2 md:text-lg"
        >
          Join Room
        </button> */}
      </form>
      <div className="flex-grow overflow-auto mb-4 p-3 bg-white rounded-md"></div>
      <form className="flex" onSubmit={sendMessage}>
        <input
          ref={inputRef}
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
