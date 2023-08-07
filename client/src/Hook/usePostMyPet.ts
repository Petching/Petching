import { useMutation, useQueryClient } from '@tanstack/react-query';
import { MyPetsType } from '../Components/User/MyPets';
import axios from 'axios';

const BASE_URL = process.env.REACT_APP_BASE_URL;

export const usePostMyPets = () => {
  const queryClient = useQueryClient();
  const { mutate: postUserCommentMutation } = useMutation(
    async ({
      name,
      species,
      gender,
      age,
      significant,
      petUmgUrl,
    }: MyPetsType) => {
      const token = localStorage.getItem('ACCESS_TOKEN');
      await axios.post(
        `${BASE_URL}/users/pet`,
        {
          name,
          species,
          gender,
          age,
          significant,
          petUmgUrl,
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
        // useId 전달해야하는지 여부 알아보기
        queryClient.invalidateQueries(['MyPets']);
      },
    },
  );
  const handlerPostMyPet = async ({
    name,
    species,
    gender,
    age,
    significant,
    petUmgUrl,
  }: MyPetsType) => {
    postUserCommentMutation({
      name,
      species,
      gender,
      age,
      significant,
      petUmgUrl,
    });
  };
  return { handlerPostMyPet };
};
