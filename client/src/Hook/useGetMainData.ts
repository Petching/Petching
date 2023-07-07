import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

export const useGetMainData = () => {
  const {
    isLoading: GetMainDataLoading,
    isError: GetMainDataError,
    data: MainData,
  } = useQuery<string[], Error>({
    queryKey: ['mainData'],
    queryFn: async () => {
      // * : 현재 json 서버와 통신 중
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
  return { GetMainDataLoading, GetMainDataError, MainData };
};
