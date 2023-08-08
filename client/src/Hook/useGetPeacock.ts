/* eslint-disable */
import { useQuery } from '@tanstack/react-query';
import axios from 'axios';
import { Axios } from '../API/api';
interface PeacockData {
  boardId: number;
  title: string;
  profileImgUrl: string;
  nickName: string;
  likes: number;
  createdAt: string;
  commentCount: number;
  checkLike: boolean;
  imgUrls: string[];
}

export const useGetPeacock = () => {
  const {
    isLoading: GetPeacockLoading,
    isError: GetPeacockError,
    data: GetPeacock,
  } = useQuery<PeacockData[], Error>({
    queryKey: ['GetPeacock'],
    queryFn: async () => {
      const response = await Axios.get('/boards?page=1&size=10');
      return response.data.data;
    },
    onError: () => {
      console.error('데이터를 받아오지 못했습니다.');
    },
    retry: failureCount => {
      return failureCount < 5;
    },
  });

  return { GetPeacockLoading, GetPeacockError, GetPeacock };
};
