export interface User {
  exists: boolean;
}

export interface ChattingRoom {
  roomId: string;
}

export interface SignupData {
  email: string;
  password: string;
  nickName: string;
}
