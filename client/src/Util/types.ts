export interface User {
  exists: boolean;
}

export interface ChattingRoom {
  roomId: string;
}

export interface SignupData {
  email: string;
  password: string;
  nickname: string;
}

export interface UserToken {
  userId: number;
  exp: number;
  iat: number;
}
