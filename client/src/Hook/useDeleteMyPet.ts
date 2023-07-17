import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';

export const useDeleteMyPet = () => {
  const queryClient = useQueryClient();
  const { mutate: deleteMyPetMutation } = useMutation(
    async () => {
      await axios.delete(``);
    },
    {
      onError: error => {
        console.error(error);
      },
      onSuccess: () => {
        queryClient.invalidateQueries(['MyPet']);
      },
    },
  );
  const handlerDeleteMyPet = async () => {
    deleteMyPetMutation();
  };
  return { handlerDeleteMyPet };
};
