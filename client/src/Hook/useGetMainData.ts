import { useQuery } from '@tanstack/react-query';
import axios from 'axios';

type DataType = {
  ImgUrls: string[];
};

export const useGetMainData = () => {
  const {
    isLoading: GetMainDataLoading,
    isError: GetMainDataError,
    data: MainData,
  } = useQuery<DataType, Error>({
    queryKey: ['mainData'],
    queryFn: async () => {
      // * : 현재 json 서버와 통신 중
      const data = await axios.get(
        'https://server.petching.net/boards/recently-created',
      );
      return data.data.data;
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
