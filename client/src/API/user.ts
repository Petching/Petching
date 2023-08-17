import { Axios, BASE_URL } from './api';

export const checkPw = async (pw: string) => {
  const data = await Axios.post(`${BASE_URL}/users/check-pw`, { password: pw });
  return data.data;
};
