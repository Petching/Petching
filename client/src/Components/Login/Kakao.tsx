import React from 'react';

const Kakao = () => {
  const restApiKey = process.env.REACT_APP_REST_API_KEY;
  const redirectUrl = 'http://localhost:3000/kakaologin';
  const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${restApiKey}&redirect_uri=${redirectUrl}&response_type=code`;

  const loginHandler = () => {
    window.location.href = kakaoAuthUrl;
  };

  return <div onClick={loginHandler}>카카오톡 로그인</div>;
};

export default Kakao;
