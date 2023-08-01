import jwtDecode from 'jwt-decode';
import { UserToken } from './types';

export const getUserIdFromToken = (isLogin: boolean, token: string | null) => {
  if (token !== null && isLogin) {
    return jwtDecode<UserToken>(token).userId;
  }
};
