import { useQuery } from '@tanstack/react-query';
import { Axios, BASE_URL } from '../API/api';

interface PeacockDetailData {
  boardId: number;
  title: string;
  content: string;
  likes: number;
  profileImgUrl: string;
  nickName: string;
  createdAt: string;
  checkLike: boolean;
  comment: string[];
  commentCount: number;
  imgUrls: string[];
}

export const useGetPeacockDetail = (boardId: number) => {
  const {
    isLoading: GetPeacockDetailLoading,
    isError: GetPeacockDetailError,
    data: GetPeacockDetail,
  } = useQuery<PeacockDetailData, Error>({
    queryKey: ['GetPeacockDetail', boardId],
    queryFn: async () => {
      const response = await Axios.get(`/boards/${boardId}`);
      return response.data.data;
    },

    onError: () => {
      console.error('데이터를 받아오지 못했습니다.');
    },
    retry: failureCount => {
      return failureCount < 5;
    },
  });

  return { GetPeacockDetailLoading, GetPeacockDetailError, GetPeacockDetail };
};
