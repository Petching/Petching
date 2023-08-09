import { useQuery } from '@tanstack/react-query';
import { Axios } from '../API/api';
import { UserProfile } from '../Util/types';

const BASE_URL = process.env.REACT_APP_BASE_URL;

export const useGetUserProfile = (userId: string | undefined) => {
  const {
    isLoading: GetUserProfileLoading,
    isError: GetUserProfileError,
    data: UserProfile,
  } = useQuery<UserProfile, Error>({
    queryKey: ['UserProfile', userId],
    queryFn: async () => {
      if (userId) {
        const data = await Axios.get(`${BASE_URL}/users/${userId}`);
        return data.data.data;
      } else {
        return {};
      }
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
