export interface User {
  exists: boolean;
}

export interface ChattingRoom {
  roomId: string;
}

export interface SignupData {
  email: string;
  //password 나중에 string으로 수정 예정
  password: string;
  nickname: string;
}
