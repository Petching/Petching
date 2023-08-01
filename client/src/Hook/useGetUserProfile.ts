import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

type UserProfile = {
  nickName: string;
  email: string;
  img?: string;
  address?: string;
};

export const useGetUserProfile = (userId: string) => {
  const {
    isLoading: GetUserProfileLoading,
    isError: GetUserProfileError,
    data: UserProfile,
  } = useQuery<UserProfile, Error>({
    queryKey: ['UserProfile', userId],
    queryFn: async () => {
      const token = localStorage.getItem('ACCESSTOKEN');
      const data = await axios.get(
        `https://server.petching.net/users/${userId}`,
        {
          headers: { Authorization: token },
        },
      );
      return data.data.data;
    },
    onError: () => {
      console.error('데이터를 받아오지 못했습니다.');
    },
    retry: () => {
      // 임의 지정. 데이터 받아오기 실패시 재시도 없음.
      return false;
    },
  });
  return { GetUserProfileLoading, GetUserProfileError, UserProfile };
};

// 유저 프로필 GET 요청 로그인 안해도 괜찮도록
// UserProfile 이미지 기능(회원가입시 임시이미지파일 등록)
