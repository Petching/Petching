import React from "react";

const SignComponent = () => {
  return (
    <>
      <div className="bg-white shadow-custom mx-auto flex flex-col  space-y-3 w-full h-full sm:w-full sm:h-full md:w-[600px] md:h-[500px] p-4 border border-#e0e0e0 rounded-3xl">
        <div className="flex-2 text-gray-300 font-semibold">펫칭</div>
        <div className="flex-2 text-left text-gray-300">아이디</div>
        <div className="flex w-full">
          <input className="flex-1 border border-gray-300 p-2 rounded-lg mr-2" />
          <button className="flex-2 border border-gray-300 text-center text-black rounded-lg p-2">
            중복확인
          </button>
        </div>

        <div className="flex-2 text-left text-gray-300">비밀번호</div>
        <input className="flex-2 border border-gray-300 p-2 rounded-lg" />
        <div className="flex-2 text-left text-gray-300">비밀번호 확인</div>
        <input className="flex-2 border border-gray-300 p-2 rounded-lg" />
        <div className="flex-2 text-left text-gray-300">닉네임</div>
        <div className="flex w-full">
          <input className="flex-1 border border-gray-300 p-2 rounded-lg mr-2" />
          <button className="flex-2 border border-gray-300 text-center text-black rounded-lg p-2">
            중복확인
          </button>
        </div>
        <br></br>
        <button className="flex-2 bg-customGreen border border-gray-300 p-2 rounded text-white">
          회원가입하기
        </button>
      </div>
    </>
  );
};

export default SignComponent;
