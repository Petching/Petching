import React, { useState } from 'react';
import googleLogo from '../Style/googleLogo.png';
import kakaoLogo from '../Style/kakaoLogo.png';
import { authenticate } from '../API/signIn';

const SignInComponent: React.FC = () => {
  const [message, setMessage] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleButtonClick = async () => {
    const success = await authenticate(email, password);
    if (success) {
      window.location.href = '/main';
    } else {
      setMessage('아이디와 비밀번호를 다시 확인해주세요');
    }
  };

  return (
    <>
      <div className=" bg-white shadow-custom mx-auto flex flex-col  space-y-3 w-full h-full sm:w-full sm:h-full md:w-[600px] md:h-[700px] p-4 border border-#e0e0e0 rounded-3xl">
        <div className="mt-11 mb-11 text-gray-300 font-semibold">펫칭</div>
        <div className="ml-4 text-left text-gray-300">아이디</div>
        <div className="flex items-center">
          <input
            type="email"
            className="ml-4  border border-gray-300 p-2 rounded-lg mr-2"
            placeholder="이메일을 입력해주세요"
          />

          <div className="text-red-500">{message}</div>
        </div>

        <div className="ml-4  text-left text-gray-300">비밀번호</div>
        <input
          type="password"
          className="ml-4 mr-7 border border-gray-300 p-2 rounded-lg"
          placeholder="비밀번호를 입력해주세요"
        />
        <br></br>
        <button
          onClick={handleButtonClick}
          className="ml-4 flex-2 bg-customGreen border border-gray-300 p-3 rounded text-gray-500 mr-7 hover:bg-green-500"
        >
          로그인하기
        </button>
        <div className="flex justify-between ">
          <div className="m-4 h-0.5 bg-gray-300 w-52"></div>
          <div className="m-1.5 fonr-semibold text-gray-400">또는</div>
          <div className="m-4 h-0.5 bg-gray-300 w-52"></div>
        </div>
        <button className="ml-4 bg-kakaoYellow border border-gray-300 p-3 rounded text-kakaoText  flex items-center justify-center mr-7 hover:bg-yellow-300">
          <img src={kakaoLogo} alt="kakao Image" className="h-7 w-7 mr-4 " />
          Kakao로 로그인하기
        </button>
        <button className="ml-4 bg-white border border-gray-300 p-3 rounded text-black  flex items-center justify-center mr-7 hover:bg-gray-200">
          <img src={googleLogo} alt="google Image" className="h-5 w-5 mr-4" />
          Google로 로그인하기
        </button>
      </div>
    </>
  );
};

export default SignInComponent;
