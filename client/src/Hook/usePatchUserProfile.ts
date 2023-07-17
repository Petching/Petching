import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

type PatchUserProfile = {
  userId: string;
  nickname: string;
  address?: string;
  img?: string;
};
export const usePatchUserProfile = (userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: patchUserProfileMutation } = useMutation(
    async ({ userId, nickname, address }: PatchUserProfile) => {
      console.log('?');
      await axios.patch(`https://server.petching.net/users/`, {
        userId,
        nickname,
        address,
      });
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
  }: PatchUserProfile) => {
    patchUserProfileMutation({
      userId,
      nickname,
      address,
    });
  };
  return { patchUserProfileMutation, handlerPatchProfile };
};
