/* eslint-disable */
import React, { useState } from 'react';
import { checkUser, checkNickname, checkEmail } from '../API/signUp';
import { useNavigate } from 'react-router-dom';
const SignComponent: React.FC = () => {
  const [message, setMessage] = useState('');
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const navigate = useNavigate();

  //중복 아이디, 중복 닉네임 확인은 다른 함수로 각각 만들어야함
  const handleCheck = async () => {
    // checkUser 함수 호출
    const data = await checkUser();
    // 유저 정보 비교
    if (data.exists) {
      setMessage('중복입니다');
    } else {
      setMessage('사용 가능');
    }
  };

  const handleEmaileCheck = async () => {
    const isDuplicate = await checkEmail(email);
    if (isDuplicate) {
      setMessage('중복입니다');
    } else {
      setMessage('사용 가능');
    }
  };

  const handleNicknameCheck = async () => {
    const isDuplicate = await checkNickname(nickname);
    if (isDuplicate) {
      setMessage('중복입니다');
    } else {
      setMessage('사용 가능');
    }
  };

  return (
    <>
      <div className=" bg-white shadow-custom mx-auto flex flex-col  space-y-3 w-full h-full sm:w-full sm:h-full md:w-[600px] md:h-[700px] p-4 border border-#e0e0e0 rounded-3xl">
        <div className="mt-11 mb-11 text-gray-300 font-semibold">펫칭</div>
        <div className="ml-4 text-left text-gray-300">아이디</div>
        <div className="flex items-center">
          <input
            className="ml-4  border border-gray-300 p-2 rounded-lg mr-2"
            placeholder="이메일을 입력해주세요"
            onChange={e => setEmail(e.target.value)}
            value={email}
          />
          <button
            onClick={handleEmaileCheck}
            className="ml-2 mr-9 flex-2 border border-gray-300 p-2 rounded text-white bg-customGreen hover:bg-green-500"
          >
            중복확인
          </button>
          <div>{message}</div>
        </div>

        <div className="ml-4  text-left text-gray-300">비밀번호</div>
        <input
          className="ml-4  mr-7 border border-gray-300 p-2 rounded-lg"
          placeholder="비밀번호를 입력해주세요"
        />
        <div className="ml-4 text-left text-gray-300">비밀번호 확인</div>
        <input
          className="ml-4 mr-7 border border-gray-300 p-2 rounded-lg"
          placeholder="비밀번호를 다시 입력해주세요"
        />
        <div className="ml-4  text-left text-gray-300">닉네임</div>
        <div className="flex w-full items-center">
          <input
            className="ml-4 border border-gray-300 p-2 rounded-lg mr-2"
            placeholder="닉네임을 입력해주세요"
            onChange={e => setNickname(e.target.value)}
            value={nickname}
          />
          <button
            onClick={handleNicknameCheck}
            className="ml-2 mr-9 flex-2 border border-gray-300 p-2 rounded text-white bg-customGreen hover:bg-green-500"
          >
            중복확인
          </button>
          <div
            className={`${
              message === '중복입니다'
                ? 'text-red-500'
                : message === '사용 가능'
                ? 'text-blue-500'
                : 'text-black'
            }`}
          >
            {message}
          </div>
        </div>
        <br></br>
        <button className="ml-4 flex-2 bg-customGreen border border-gray-300 p-2 rounded text-white mr-7 hover:bg-green-500">
          회원가입하기
        </button>
        <button
          onClick={() => navigate('/signin')}
          className="flex items-center justify-center"
        >
          로그인하러가기
        </button>
      </div>
    </>
  );
};

export default SignComponent;
