import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

const BASE_URL = process.env.REACT_APP_BASE_URL;

export const useDeleteUserProfile = (userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: deleteUserProfileMutation } = useMutation(
    async (userId: string) => {
      const token = localStorage.getItem('ACCESS_TOKEN');
      await axios.delete(`${BASE_URL}/users/${userId}`, {
        headers: { Authorization: token },
      });
    },
    {
      onError: error => {
        console.error(error);
      },
      onSuccess: () => {
        queryClient.invalidateQueries(['userProfile', userId]);
        localStorage.removeItem('ACCESS_TOKEN');
      },
    },
  );
  const handlerDeleteUserProfile = async (userId: string) => {
    deleteUserProfileMutation(userId);
  };
  return { handlerDeleteUserProfile };
};
