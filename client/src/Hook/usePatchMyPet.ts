import { useMutation, useQueryClient } from '@tanstack/react-query';
import { MyPetsType } from '../Components/User/MyPets';
import { Axios } from '../API/api';

const BASE_URL = process.env.REACT_APP_API_SERVER;

export const usePatchMyPet = (userId: string) => {
  const queryClient = useQueryClient();
  const { mutate: patchMyPetMutation } = useMutation(
    async ({
      name,
      species,
      gender,
      age,
      significant,
      petImgUrl,
      myPetId,
    }: MyPetsType) => {
      await Axios.patch(`${BASE_URL}/users/pets`, {
        name,
        species,
        gender,
        age,
        significant,
        petImgUrl,
        myPetId,
      });
    },
    {
      onError: error => {
        console.error(error);
      },
      onSuccess: () => {
        // userId 등 id가 필요한지 알아보기
        queryClient.invalidateQueries(['MyPets', userId]);
      },
    },
  );
  const handlerPatchMyPet = async ({
    name,
    species,
    gender,
    age,
    significant,
    petImgUrl,
    myPetId,
  }: MyPetsType) => {
    patchMyPetMutation({
      name,
      species,
      gender,
      age,
      significant,
      petImgUrl,
      myPetId,
    });
  };
  return { patchMyPetMutation, handlerPatchMyPet };
};
