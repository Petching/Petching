import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const KakaoLogin = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const url = new URL(window.location.href);
    const accessToken = url.searchParams.get('access_token');
    const refreshToken = url.searchParams.get('refresh_token');

    if (accessToken) {
      localStorage.setItem('ACCESS_TOKEN', accessToken);
    }

    if (refreshToken) {
      const date = new Date();
      date.setDate(date.getDate() + 7);
      document.cookie = `REFRESH_TOKEN=${refreshToken}; expires=${date.toUTCString()}`;
    }

    navigate('/', { replace: true });
  }, [navigate]);

  return <div></div>;
};

export default KakaoLogin;
