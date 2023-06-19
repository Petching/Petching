import React from "react";

const SignComponent = () => {
  return (
    <>
      <div className="bg-white shadow-custom mx-auto flex flex-col  space-y-3 w-full h-full sm:w-full sm:h-full md:w-[600px] md:h-[700px] p-4 border border-#e0e0e0 rounded-3xl">
        <div className="mt-11 mb-11 text-gray-300 font-semibold">펫칭</div>
        <div className=" text-left text-gray-300">아이디</div>
        <div className="flex w-full">
          <input
            className=" border border-gray-300 p-2 rounded-lg mr-2"
            placeholder="이메일을 입력해주세요"
          />
          <button className="flex-2 border border-gray-300 p-2 rounded text-white bg-customGreen hover:bg-green-500">
            중복확인
          </button>
        </div>

        <div className=" text-left text-gray-300">비밀번호</div>
        <input
          className=" border border-gray-300 p-2 rounded-lg"
          placeholder="비밀번호를 입력해주세요"
        />
        <div className=" text-left text-gray-300">비밀번호 확인</div>
        <input
          className=" border border-gray-300 p-2 rounded-lg"
          placeholder="비밀번호를 다시 입력해주세요"
        />
        <div className=" text-left text-gray-300">닉네임</div>
        <div className="flex w-full">
          <input
            className=" border border-gray-300 p-2 rounded-lg mr-2"
            placeholder="닉네임을 입력해주세요"
          />
          <button className="flex-2 border border-gray-300 p-2 rounded text-white bg-customGreen hover:bg-green-500">
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
