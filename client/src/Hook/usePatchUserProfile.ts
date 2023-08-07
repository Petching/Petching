import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

type PatchUserProfile = {
  userId: string;
  nickname: string;
  password: string;
  address?: string;
  profileImgUrl?: string;
};

const BASE_URL = process.env.REACT_APP_BASE_URL;

export const usePatchUserProfile = (userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: patchUserProfileMutation } = useMutation(
    async ({
      userId,
      nickname,
      address,
      password,
      profileImgUrl,
    }: PatchUserProfile) => {
      const token = localStorage.getItem('ACCESS_TOKEN');
      await axios.patch(
        `${BASE_URL}/users/`,
        {
          userId,
          nickname,
          address,
          password,
          profileImgUrl,
        },
        {
          headers: { Authorization: token },
        },
      );
    },
    {
      onError: error => {
        console.error(error);
      },
      onSuccess: () => {
        queryClient.invalidateQueries(['UserProfile', userId]);
      },
    },
  );
  const handlerPatchProfile = async ({
    userId,
    nickname,
    address,
    profileImgUrl,
    password,
  }: PatchUserProfile) => {
    patchUserProfileMutation({
      userId,
      nickname,
      address,
      profileImgUrl,
      password,
    });
  };
  return { patchUserProfileMutation, handlerPatchProfile };
};
