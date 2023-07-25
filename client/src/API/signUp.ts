/* eslint-disable */
import { User, SignupData } from '../Util/types';
import axios from 'axios';
const BASE_URL = process.env.REACT_APP_BASE_URL;

export const checkUser = async () => {
  // 백엔드 API 호출하여 DB에서 유저 정보 가져오기
  const response = await fetch('/api/check-user');
  const data: User = await response.json();
  return data;
};

export const signUpUser = async (data: SignupData) => {
  try {
    const response = await axios.post(
      `https://server.petching.net/users/sign-up`,
      data,
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );
    return response;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const checkEmail = async (email: string) => {
  try {
    const response = await axios.post(
      `https://server.petching.net/users/sign-up`,
      { email },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );

    return response.data.isDuplicate;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const checkNickname = async (nickname: string) => {
  try {
    const response = await axios.post(
      `https://server.petching.net/users/sign-up`,
      { nickname },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );

    return response.data.isDuplicate;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
