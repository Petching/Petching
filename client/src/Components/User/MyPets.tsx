import { useState } from 'react';
import AddPet from './AddPet';
import PetCard from './PetCard';
import { useGetMyPets } from '../../Hook/useGetMyPets';

export type MyPetsType = {
  // img: string;
  name: string;
  kind: string;
  gender: string;
  age: string;
  weight?: string;
  vaccination?: string;
  etc?: string;
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
        {/* 나중에 데이터가 어떤 형식으로 오는지 보고 수정할 것 */}
        {MyPets &&
          MyPets.map((ele, idx) => (
            <PetCard
              key={idx}
              name={ele.name}
              kind={ele.kind}
              gender={ele.gender}
              age={ele.age}
              weight={ele.weight}
              vaccination={ele.vaccination}
              etc={ele.etc}
            />
          ))}
      </div>
      <AddPet open={open} setOpen={setOpen} />
    </div>
  );
};

export default MyPets;
