import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

type UserProfile = {
  name: string;
  img: string;
  address: string;
  email: string;
};

export const useGetUserProfile = (userId: string) => {
  const {
    isLoading: GetUserProfileLoading,
    isError: GetUserProfileError,
    data: UserProfile,
  } = useQuery<UserProfile, Error>({
    queryKey: ['UserProfile', userId],
    queryFn: async () => {
      // const data = await axios.get('http://localhost:3001/profile');
      const data = await axios.get(
        `https://fcf3-118-32-224-80.ngrok-free.app/users/1`,
        {
          headers: {
            'Content-Type': 'application/json',
            'ngrok-skip-browser-warning': '69420',
          },
        },
      );
      return data.data;
    },
    onError: () => {
      console.error('데이터를 받아오지 못했습니다.');
    },
    retry: failureCount => {
      return failureCount < 5;
    },
  });
  return { GetUserProfileLoading, GetUserProfileError, UserProfile };
};
