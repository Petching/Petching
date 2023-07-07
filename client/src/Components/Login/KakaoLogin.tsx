import React from 'react';
import { useEffect } from 'react';
import axios from 'axios';
// import { setCookie } from "../components/social/Cookie";
import { useNavigate } from 'react-router-dom';

const KakaoLogin = () => {
  const goToMain = () => {
    navigate('/');
  };
  const navigate = useNavigate();
  const code: string | null = new URL(window.location.href).searchParams.get(
    'code',
  );
  const BASE_URL = process.env.REACT_APP_BASE_URL;

  useEffect(() => {
    const kakao = async () => {
      return await axios
        .get(`${BASE_URL}/api/v1/members/kakaoLogin?code=${code}`)

        .then(response => {
          const data = response.data;
          if (data.message === 'success') {
            localStorage.setItem('TOKEN', data.token);
            goToMain();
            alert('로그인 성공');
          } else {
            goToMain();
            alert('로그인 실패');
          }
        });
    };
    if (code) {
      kakao();
    }
  }, [code, navigate]);

  return <div></div>;
};

export default KakaoLogin;
