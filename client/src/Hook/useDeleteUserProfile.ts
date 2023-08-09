import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Axios } from '../API/api';

const BASE_URL = process.env.REACT_APP_BASE_URL;

export const useDeleteUserProfile = (userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: deleteUserProfileMutation } = useMutation(
    async (userId: string) => {
      await Axios.delete(`${BASE_URL}/users/${userId}`);
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
