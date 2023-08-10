import { Axios } from './api';

const BASE_URL = process.env.REACT_APP_API_SERVER;

export const checkPw = async (pw: string) => {
  const data = await Axios.post(`${BASE_URL}/users/check-pw`, { password: pw });
  return data.data;
};
