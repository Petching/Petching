/* eslint-disable */
import React, { useState } from 'react';
import googleLogo from '../../Style/googleLogo.png';
import kakaoLogo from '../../Style/kakaoLogo.png';
import { authenticate } from '../../API/signIn';
import { useNavigate } from 'react-router-dom';
import useLoginStore from '../../store/login';

const SignInComponent: React.FC = () => {
  const [message, setMessage] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const { setLogin } = useLoginStore();

  const Google = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    const googleAuthUrl =
      'https://accounts.google.com/o/oauth2/auth?' +
      `client_id=${process.env.REACT_APP_GOOGLE_API_KEY}&` +
      `redirect_uri=${process.env.REACT_APP_GOOGLE_API_KEY}&` +
      'response_type=token&' +
      'scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile';
    window.location.href = googleAuthUrl;
  };

  const Kakao = async () => {
    const restApiKey = process.env.REACT_APP_KAKAO_API_KEY;
    const redirectUrl = process.env.REACT_APP_KAKAO_REDIRECT_URL;
    const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${restApiKey}&redirect_uri=${redirectUrl}&response_type=code`;

    window.location.href =
      'https://server.petching.net/oauth2/authorization/kakao';
  };

  const handleButtonClick = async () => {
    const success = await authenticate(email, password);
    if (success) {
      navigate('/');
      setLogin();
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
            onChange={event => setEmail(event.target.value)}
          />

          <div className="text-red-500">{message}</div>
        </div>

        <div className="ml-4  text-left text-gray-300">비밀번호</div>
        <input
          type="password"
          className="ml-4 mr-7 border border-gray-300 p-2 rounded-lg"
          placeholder="비밀번호를 입력해주세요"
          onChange={event => setPassword(event.target.value)}
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

        <button
          onClick={Kakao}
          className="ml-4 bg-kakaoYellow border border-gray-300 p-3 rounded text-kakaoText  flex items-center justify-center mr-7 hover:bg-yellow-300"
        >
          <img src={kakaoLogo} alt="kakao Image" className="h-7 w-7 mr-4 " />
          카카오톡 로그인
        </button>

        <button
          onClick={Google}
          className="ml-4 bg-white border border-gray-300 p-3 rounded text-black  flex items-center justify-center mr-7 hover:bg-gray-200"
        >
          <img src={googleLogo} alt="google Image" className="h-5 w-5 mr-4" />
          구글 로그인
        </button>
        <button
          onClick={() => navigate('/signup')}
          className="flex items-center justify-center"
        >
          회원가입하러가기
        </button>
      </div>
    </>
  );
};

export default SignInComponent;
