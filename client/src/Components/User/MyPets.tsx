import { useState } from 'react';
import AddPet from './AddPet';
import PetCard from './PetCard';
import { useGetMyPets } from '../../Hook/useGetMyPets';

export type MyPetsType = {
  // img: string;
  // name: string;
  // kind: string;
  // gender: string;
  // age: string;
  // weight?: string;
  // vaccination?: string;
  // etc?: string;

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
  console.log(MyPets);
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
              species={ele.species}
              gender={ele.gender}
              age={ele.age}
              petImgUrl={ele.petImgUrl}
              significant={ele.significant}
              myPetId={ele.myPetId}
              userId={userId}
            />
          ))}
        {/* 디자인을 보기 위해 만든 임시 카드. 추후 데이터를 받아오면 지울 것. */}
        {/* <PetCard
          name="이름"
          species="종"
          gender="성별"
          age={1}
          petImgUrl="백신"
          significant="기타"
        /> */}
      </div>
      <AddPet open={open} setOpen={setOpen} userId={userId} />
    </div>
  );
};

export default MyPets;
