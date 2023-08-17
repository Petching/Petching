import { useMutation, useQueryClient } from '@tanstack/react-query';
import { Axios, BASE_URL } from '../API/api';

export const useDeleteMyPet = (myPetId: number, userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: deleteMyPetMutation } = useMutation(
    async (myPetId: number) => {
      await Axios.delete(`${BASE_URL}/users/pets/${myPetId}`);
    },
    {
      onError: error => {
        console.error(error);
      },
      onSuccess: () => {
        queryClient.invalidateQueries(['MyPets', userId]);
      },
    },
  );
  const handlerDeleteMyPet = async () => {
    deleteMyPetMutation(myPetId);
  };
  return { handlerDeleteMyPet };
};
