/* eslint-disable */
import axios from 'axios';
const BASE_URL = process.env.REACT_APP_API_SERVER;

export const authenticate = async (email: string, password: string) => {
  try {
    const response = await axios.post(
      `${BASE_URL}/login`,
      { email, password },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );

    if (response.status === 200) {
      console.log(response);
      if (response.headers['authorization']) {
        const accessToken = response.headers['authorization'];
        const refreshToken = response.headers['refresh'];
        localStorage.setItem('ACCESS_TOKEN', accessToken);
        const date = new Date();
        //쿠키 만료시간 7일뒤
        date.setDate(date.getDate() + 7);
        document.cookie = `REFRESH_TOKEN=${refreshToken}; expires=${date.toUTCString()}; path=/`;
        return true;
      }
    }
  } catch (error) {
    console.error(error);
    throw error;
  }
};
