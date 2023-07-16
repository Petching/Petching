import { Dispatch, SetStateAction, useRef } from 'react';
import { usePostMyPets } from '../../Hook/usePostMyPet';

type Props = {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
};
const AddPet: React.FC<Props> = ({ open, setOpen }) => {
  const nameRef = useRef<HTMLInputElement>(null);
  const kindRef = useRef<HTMLInputElement>(null);
  const genderRef = useRef<HTMLInputElement>(null);
  const ageRef = useRef<HTMLInputElement>(null);
  const weightRef = useRef<HTMLInputElement>(null);
  const vaccinationRef = useRef<HTMLInputElement>(null);
  const etcRef = useRef<HTMLInputElement>(null);
  const { handlerPostMyPet } = usePostMyPets();
  const submitHandler = () => {
    const { value: name } = nameRef.current!;
    const { value: kind } = kindRef.current!;
    const { value: gender } = genderRef.current!;
    const { value: age } = ageRef.current!;
    const { value: weight } = weightRef.current!;
    const { value: vaccination } = vaccinationRef.current!;
    const { value: etc } = etcRef.current!;
    handlerPostMyPet({ name, kind, gender, age, weight, vaccination, etc });
    setOpen(false);
  };
  return (
    <form
      className={`border rounded w-[400px] p-5 flex flex-col justify-center items-center fixed top-1/2 right-1/2 translate-x-1/2 -translate-y-1/2 bg-white ${
        open ? 'block' : 'hidden'
      }`}
    >
      <label className="w-32 h-32 rounded overflow-hidden border relative block cursor-pointer hover:border-4">
        <div className="absolute bottom-0 left-0 w-full h-1/2 bg-white text-center p-2 opacity-80">
          이미지 <br />
          입력
        </div>
        <img
          src="https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_1280.jpg"
          alt="입력된 이미지"
          className="w-full h-full"
        />
        <input
          id="attach-file"
          type="file"
          accept="image/*"
          className="hidden"
        />
      </label>
      <div className="flex flex-col my-5">
        <label className="flex justify-between w-[280px]">
          <p>이름</p>
          <input className="border" ref={nameRef} />
        </label>
        <label className="flex justify-between">
          <p>종(세부종)</p>
          <input className="border" ref={kindRef} />
        </label>
        <label className="flex justify-between">
          <p>성별</p>
          <input className="border" ref={genderRef} />
        </label>
        <label className="flex justify-between">
          <p>나이</p>
          <input className="border" ref={ageRef} />
        </label>
        <label className="flex justify-between">
          <p>몸무게</p>
          <input className="border" ref={weightRef} />
        </label>
        <label className="flex justify-between">
          <p>예방접종 유무</p>
          <input className="border" ref={vaccinationRef} />
        </label>
        <label className="flex justify-between">
          <p>특이사항</p>
          <input className="border" ref={etcRef} />
        </label>
      </div>
      <div>
        <button className="mr-5" onClick={() => setOpen(false)} type="button">
          취소
        </button>
        <button type="submit" onClick={submitHandler}>
          입력
        </button>
      </div>
    </form>
  );
};

export default AddPet;
