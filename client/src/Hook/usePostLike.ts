import { useState } from 'react';
import { Axios, BASE_URL } from '../API/api';

export const usePostLike = async (boardId: number) => {
  const [isLike, setIsLike] = useState(false);
  const accessToken = localStorage.getItem('ACCESS_TOKEN');

  if (!accessToken) {
    // 액세스 토큰이 없으면 처리
    throw new Error('Access token not found');
  }
  if (isLike) {
    // 이미 좋아요 처리 중인 경우 중복 요청 방지
    return;
  }
  try {
    setIsLike(true); // 좋아요 처리 시작

    const response = await Axios.post(
      `${BASE_URL}/boards/${boardId}/like`,
      {},
      {
        headers: {
          Authorization: accessToken,
        },
      },
    );

    return response.data;
  } catch (error) {
    console.error('데이터를 받아오지 못했습니다.');
  } finally {
    setIsLike(false); // 좋아요 처리 완료
  }
};
