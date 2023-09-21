import { useMutation, useQueryClient } from '@tanstack/react-query';
import { QueryKey } from 'react-query';
import axios from 'axios';

export interface BoardData {
  title: string;
  content: string;
  imgUrls: string[];
}

const createBoard = async (boardData: BoardData) => {
  const accessToken = localStorage.getItem('ACCESS_TOKEN');

  if (!accessToken) {
    // 액세스 토큰이 없으면 처리
    throw new Error('Access token not found');
  }

  const response = await axios.post(
    'https://server.petching.net/boards',
    boardData,
    {
      headers: {
        Authorization: `${accessToken}`,
      },
    },
  );
  return response.data;
};

export const useCreateBoard = () => {
  const queryClient = useQueryClient();

  return useMutation((boardData: BoardData) => createBoard(boardData), {
    onSuccess: () => {
      // 성공적인 뮤테이션 후 데이터를 무효화하고 다시 불러옵니다.
      const queryKey: QueryKey = ['boards'];
      queryClient.invalidateQueries(queryKey);
    },
  });
};
