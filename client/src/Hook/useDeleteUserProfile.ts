import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

export const useDeleteUserProfile = (userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: deleteUserProfileMutation } = useMutation(
    async (userId: string) => {
      await axios.delete(`https://server.petching.net/users/${userId}`);
    },
    {
      onError: error => {
        console.error(error);
      },
      onSuccess: () => {
        queryClient.invalidateQueries(['userProfile', userId]);
      },
    },
  );
  const handlerDeleteUserProfile = async (userId: string) => {
    deleteUserProfileMutation(userId);
  };
  return { handlerDeleteUserProfile };
};
