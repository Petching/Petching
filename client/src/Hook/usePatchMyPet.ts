import { useMutation, useQueryClient } from '@tanstack/react-query';
import { MyPetsType } from '../Components/User/MyPets';
import axios from 'axios';

const BASE_URL = process.env.REACT_APP_BASE_URL;

export const usePatchMyPet = () => {
  const queryClient = useQueryClient();
  const { mutate: patchMyPetMutation } = useMutation(
    async ({
      name,
      species,
      gender,
      age,
      significant,
      petUmgUrl,
    }: MyPetsType) => {
      // ! : 마이펫 아이디 요청하기
      // ! : 이미지 안들어오는거 문의하기
      const token = localStorage.getItem('ACCESS_TOKEN');
      await axios.patch(
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
        // userId 등 id가 필요한지 알아보기
        queryClient.invalidateQueries(['MyPets']);
      },
    },
  );
  const handlerPatchMyPet = async ({
    name,
    species,
    gender,
    age,
    significant,
    petUmgUrl,
  }: MyPetsType) => {
    patchMyPetMutation({
      name,
      species,
      gender,
      age,
      significant,
      petUmgUrl,
    });
  };
  return { patchMyPetMutation, handlerPatchMyPet };
};
