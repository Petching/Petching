import { useMutation, useQueryClient } from '@tanstack/react-query';
import { MyPetsType } from '../Components/User/MyPets';
import { Axios, BASE_URL } from '../API/api';

export const usePostMyPets = (userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: postUserCommentMutation } = useMutation(
    async ({
      name,
      species,
      gender,
      age,
      significant,
      petImgUrl,
    }: MyPetsType) => {
      await Axios.post(`${BASE_URL}/users/pets`, {
        name,
        species,
        gender,
        age,
        significant,
        petImgUrl,
      });
    },
    {
      onError: error => {
        console.error(error);
      },
      onSuccess: () => {
        // useId 전달해야하는지 여부 알아보기
        queryClient.invalidateQueries(['MyPets', userId]);
      },
    },
  );
  const handlerPostMyPet = async ({
    name,
    species,
    gender,
    age,
    significant,
    petImgUrl,
  }: MyPetsType) => {
    postUserCommentMutation({
      name,
      species,
      gender,
      age,
      significant,
      petImgUrl,
    });
  };
  return { handlerPostMyPet };
};
