/* eslint-disable */
import { User, SignupData } from '../Util/types';
import axios from 'axios';
const BASE_URL = process.env.REACT_APP_API_SERVER;
import { Axios } from '../API/api';

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
      `https://server.petching.net/users/check-id`,
      { email },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );

    console.log(response);
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const checkNickname = async (nickName: string) => {
  try {
    const response = await axios.post(
      `https://server.petching.net/users/check-nick`,
      { nickName },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );
    console.log(response);
    return response.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};
