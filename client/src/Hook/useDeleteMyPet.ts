import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';
const BASE_URL = process.env.REACT_APP_BASE_URL;

export const useDeleteMyPet = (myPetId: number, userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: deleteMyPetMutation } = useMutation(
    async (myPetId: number) => {
      const token = localStorage.getItem('ACCESS_TOKEN');
      await axios.delete(`${BASE_URL}/users/pets/${myPetId}`, {
        headers: { Authorization: token },
      });
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
