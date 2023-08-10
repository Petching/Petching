import { useState } from 'react';
import AddPet from './AddPet';
import PetCard from './PetCard';
import { useGetMyPets } from '../../Hook/useGetMyPets';

export type MyPetsType = {
  name: string;
  species: string;
  gender: string;
  age: number;
  significant: string;
  petImgUrl: string;
  myPetId?: number;
  userId?: string;
};

const MyPets: React.FC<{ userId: string }> = ({ userId }) => {
  const [open, setOpen] = useState<boolean>(false);
  const { MyPets } = useGetMyPets(userId);
  return (
    <div className="relative py-8 w-full">
      <button
        className="absolute top-0 right-0 hover:text-customGreen"
        onClick={() => setOpen(true)}
      >
        등록
      </button>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {MyPets &&
          MyPets.map((ele, idx) => (
            <PetCard
              key={idx}
              name={ele.name}
              species={ele.species}
              gender={ele.gender}
              age={ele.age}
              petImgUrl={ele.petImgUrl}
              significant={ele.significant}
              myPetId={ele.myPetId}
              userId={userId}
            />
          ))}
      </div>
      <AddPet open={open} setOpen={setOpen} userId={userId} />
    </div>
  );
};

export default MyPets;
