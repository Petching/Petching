import { useQuery } from '@tanstack/react-query';
import { Axios, BASE_URL } from '../API/api';
import { UserPostType } from '../Util/types';

export const useGetMyPostToPeacock = (userId: string, page: number) => {
  const {
    isLoading: GetMyPostToPeacockLoading,
    isError: GetMyPostToPeacockError,
    data: MyPostToPeacock,
  } = useQuery<UserPostType, Error>({
    queryKey: ['MyPostToPeacock', userId],
    queryFn: async () => {
      // 추후 peocock 데이터 받아올 수 있게 되면 api 주소 바꿀 것
      const data = await Axios.get(
        `${BASE_URL}/careposts/my-page/${userId}?page=${page}&size=9`,
      );
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
  return {
    GetMyPostToPeacockLoading,
    GetMyPostToPeacockError,
    MyPostToPeacock,
  };
};
