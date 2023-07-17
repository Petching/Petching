import { useQuery } from '@tanstack/react-query';
import axios from 'axios';
import { MyPetsType } from '../Components/User/MyPets';

export const useGetMyPets = (userId: string) => {
  const {
    isLoading: GetMyPetsLoading,
    isError: GetMyPetsError,
    data: MyPets,
  } = useQuery<MyPetsType[], Error>({
    queryKey: ['MyPets', userId],
    queryFn: async () => {
      const data = await axios.get('http://localhost:3001/data');
      return data.data;
    },
    onError: () => {
      console.error('데이터를 받아오지 못했습니다.');
    },
    retry: failureCount => {
      return failureCount < 5;
    },
  });
  return { GetMyPetsLoading, GetMyPetsError, MyPets };
};
