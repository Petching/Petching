import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Axios } from '../API/api';
import { removeCookie } from '../Util/getCookie';
import useLoginStore from '../store/login';

const BASE_URL = process.env.REACT_APP_API_SERVER;

export const useDeleteUserProfile = (userId: string) => {
  const queryClient = useQueryClient();
  const { setLogout } = useLoginStore();
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
        removeCookie();
        setLogout();
      },
    },
  );
  const handlerDeleteUserProfile = async (userId: string) => {
    deleteUserProfileMutation(userId);
  };
  return { handlerDeleteUserProfile };
};
