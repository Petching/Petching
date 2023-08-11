import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Axios, BASE_URL } from '../API/api';
import { PatchUserProfile } from '../Util/types';

export const usePatchUserProfile = (userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: patchUserProfileMutation } = useMutation(
    async ({
      userId,
      nickName,
      address,
      password,
      profileImgUrl,
    }: PatchUserProfile) => {
      await Axios.patch(`${BASE_URL}/users/`, {
        userId,
        nickName,
        address,
        password,
        profileImgUrl,
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
    nickName,
    address,
    profileImgUrl,
    password,
  }: PatchUserProfile) => {
    patchUserProfileMutation({
      userId,
      nickName,
      address,
      profileImgUrl,
      password,
    });
  };
  return { patchUserProfileMutation, handlerPatchProfile };
};
