import axios from 'axios';
import { getCookie } from '../Util/getCookie';

const BASE_URL = process.env.REACT_APP_API_SERVER;

export const Axios = axios.create({
  baseURL: 'https://server.petching.net',
  //   timeout: 10000,
  //   withCredentials: true,
});

// 새로운 액세스 토큰을 받아오는 작업을 수행하는 별도의 Axios 인스턴스
const AxiosForTokenRefresh = axios.create({
  baseURL: 'https://server.petching.net',
});

//request 소매치기
Axios.interceptors.request.use(
  async function (config) {
    const accessToken = localStorage.getItem('ACCESS_TOKEN');
    const refreshToken = getCookie('REFRESH_TOKEN');
    console.log(refreshToken);
    if (!accessToken && refreshToken) {
      // 로그아웃 후 refreshToken은 있고, accessToken은 없음
      //refreshtoken만 보내줌
      try {
        // 새로운 액세스 토큰을 받아오는 작업은 별도의 Axios 인스턴스를 사용하여 수행
        const response = await AxiosForTokenRefresh.post(
          `/api/jwt`,
          {},
          {
            headers: {
              'Content-Type': 'application/json',
              Refresh: refreshToken,
            },
          },
        );
        //응답이 성공적이면 헤더에 새로운 accessToken 받아오기
        if (response.status === 201) {
          const newAccessToken = response.data.accessToken;
          localStorage.setItem('ACCESS_TOKEN', newAccessToken);
          config.headers['Authorization'] = `Bearer ${newAccessToken}`;
        }
      } catch (error) {
        console.error(error);
      }
    } else {
      console.log('아무것도 아닙니다');
    }

    return config;
  },
  function (error) {
    return Promise.reject(error);
  },
);

Axios.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    if (error.response.status === 401) {
      window.location.href = `${BASE_URL}/signin`;
    }
    return Promise.reject(error);
  },
);
