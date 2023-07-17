import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';
import { MyPetsType } from '../Components/User/MyPets';

export const usePatchMyPet = () => {
  const queryClient = useQueryClient();
  const { mutate: patchMyPetMutation } = useMutation(
    async ({
      // img,
      name,
      kind,
      gender,
      age,
      weight,
      vaccination,
      etc,
    }: MyPetsType) => {
      await axios.patch(`https://server.petching.net/`, {
        // img,
        name,
        kind,
        gender,
        age,
        weight,
        vaccination,
        etc,
      });
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
    // img,
    name,
    kind,
    gender,
    age,
    weight,
    vaccination,
    etc,
  }: MyPetsType) => {
    patchMyPetMutation({
      // img,
      name,
      kind,
      gender,
      age,
      weight,
      vaccination,
      etc,
    });
  };
  return { patchMyPetMutation, handlerPatchMyPet };
};
