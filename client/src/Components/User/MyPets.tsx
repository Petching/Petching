import { useState } from 'react';
import AddPet from './AddPet';
import PetCard from './PetCard';

const MyPets = () => {
  const [open, setOpen] = useState<boolean>(false);
  return (
    <div className="relative py-8 w-full">
      <button
        className="absolute top-0 right-0 hover:text-[#5fb7a1]"
        onClick={() => setOpen(true)}
      >
        등록
      </button>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* 나중에 데이터 받아서 map으로 돌릴 것 */}
        <PetCard />
        <PetCard />
      </div>
      <AddPet open={open} setOpen={setOpen} />
    </div>
  );
};

export default MyPets;
