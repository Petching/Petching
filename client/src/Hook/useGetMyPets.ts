import { useQuery } from '@tanstack/react-query';
import { MyPetsType } from '../Components/User/MyPets';
import { Axios, BASE_URL } from '../API/api';

export const useGetMyPets = (userId: string) => {
  const {
    isLoading: GetMyPetsLoading,
    isError: GetMyPetsError,
    data: MyPets,
  } = useQuery<MyPetsType[], Error>({
    queryKey: ['MyPets', userId],
    queryFn: async () => {
      const data = await Axios.get(`${BASE_URL}/users/pets/${userId}`);
      return data.data;
    },
    onError: () => {
      console.error('데이터를 받아오지 못했습니다.');
    },
    retry: () => {
      // 임의 지정. 데이터 받아오기 실패시 재시도 없음.
      return false;
    },
  });
  return { GetMyPetsLoading, GetMyPetsError, MyPets };
};
