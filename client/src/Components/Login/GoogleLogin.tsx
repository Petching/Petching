import React from 'react';
import { useEffect } from 'react';
import axios from 'axios';
// import { setCookie } from "../components/social/Cookie";
import { useNavigate } from 'react-router-dom';

const GoogleLogin = () => {
  const goToMain = () => {
    navigate('/');
  };
  const navigate = useNavigate();
  const code: string | null = new URL(window.location.href).searchParams.get(
    'code',
  );

  useEffect(() => {
    const google = async () => {
      return await axios
        .get(`https://server.petching.net/kakaoLogin?code=${code}`)

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
      google();
    }
  }, [code, navigate]);

  return <div></div>;
};

export default GoogleLogin;
