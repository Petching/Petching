import { User } from '../Util/types';
import axios from 'axios';

export const checkUser = async () => {
  // 백엔드 API 호출하여 DB에서 유저 정보 가져오기
  const response = await fetch('/api/check-user');
  const data: User = await response.json();
  return data;
};

export const checkEmail = async (email: string) => {
  try {
    const response = await axios.post(
      'https://fcf3-118-32-224-80.ngrok-free.app/users/sign-up',
      { email },
      {
        headers: {
          'Content-Type': 'application/json',
          'ngrok-skip-browser-warning': '69420',
        },
      },
    );

    return response.data.isDuplicate;
  } catch (error) {
    // Handle error if the request fails
    console.error(error);
    throw error;
  }
};

export const checkNickname = async (nickname: string) => {
  try {
    const response = await axios.post(
      'https://fcf3-118-32-224-80.ngrok-free.app/users/sign-up',
      { nickname },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );

    return response.data.isDuplicate;
  } catch (error) {
    // Handle error if the request fails
    console.error(error);
    throw error;
  }
};
