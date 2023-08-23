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

export interface UserToken {
  userId: number;
  exp: number;
  iat: number;
}

export interface UserProfile {
  nickName: string;
  email: string;
  profileImgUrl: string;
  userDivision: boolean;
  socialType: string;
  address?: string;
}

export interface PatchUserProfile {
  userId: string;
  nickName: string;
  password?: string;
  address?: string;
  profileImgUrl?: string;
}

export type GoToFunction = (path: string) => void;

export type DummyDataItem = {
  id: number;
  q: string;
  a: string;
  조회수: number;
};

export type CardProps = {
  title: string;
  locationTag: string;
  petSizes: string[];
  nickName: string;
  profileImgUrl: string;
  imgUrls: string[];
  postId: number;
};

export interface UserPostType {
  data: CardProps[];
  pageInfo: {
    page: number;
    size: number;
    totalElements: number;
    totalPages: number;
  };
}
