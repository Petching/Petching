import { useMutation, useQueryClient } from '@tanstack/react-query';
import axios from 'axios';
import { MyPetsType } from '../Components/User/MyPets';

export const usePostMyPets = () => {
  const queryClient = useQueryClient();
  const { mutate: postUserCommentMutation } = useMutation(
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
      await axios.post(`https://server.petching.net/`, {
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
        // useId 전달해야하는지 여부 알아보기
        queryClient.invalidateQueries(['MyPets']);
      },
    },
  );
  const handlerPostMyPet = async ({
    // img,
    name,
    kind,
    gender,
    age,
    weight,
    vaccination,
    etc,
  }: MyPetsType) => {
    postUserCommentMutation({
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
  return { handlerPostMyPet };
};
