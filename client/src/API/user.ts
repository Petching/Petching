import { Axios } from './api';

const BASE_URL = process.env.REACT_APP_BASE_URL;

export const checkPw = async (pw: string) => {
  const data = await Axios.post(`${BASE_URL}/users/check-pw`, { password: pw });
  return data.data;
};
