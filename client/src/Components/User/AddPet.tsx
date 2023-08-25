import { Dispatch, SetStateAction, useRef, useState } from 'react';
import { usePostMyPets } from '../../Hook/usePostMyPet';
import { postImgHandler } from '../../Util/postImg';

type Props = {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
  userId: string;
};
const AddPet: React.FC<Props> = ({ open, setOpen, userId }) => {
  const [img, setImg] = useState<string>(
    'https://s3.ap-northeast-2.amazonaws.com/petching.image/dog-5960092_1920.jpg',
  );
  const [imgFiles, setImgFiles] = useState<File>();

  const changeImgHandler = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const target = e.currentTarget;
    const files = (target.files as FileList)[0];
    if (!files) return;
    const reader = new FileReader();
    reader.readAsDataURL(files);
    reader.onloadend = () => {
      setImg(reader.result as string);
    };
    setImgFiles(files);
  };

  const nameRef = useRef<HTMLInputElement>(null);
  const speciesRef = useRef<HTMLInputElement>(null);
  const genderRef = useRef<HTMLInputElement>(null);
  const ageRef = useRef<HTMLInputElement>(null);
  const significantRef = useRef<HTMLInputElement>(null);
  const { handlerPostMyPet } = usePostMyPets(userId);

  const submitHandler = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();
    const { value: name } = nameRef.current!;
    const { value: species } = speciesRef.current!;
    const { value: gender } = genderRef.current!;
    const age = Number(ageRef.current!.value);
    const { value: significant } = significantRef.current!;
    // handlerPostMyPet({ name, species, gender, age, weight, vaccination, etc });
    handlerPostMyPet({
      name,
      species,
      gender,
      age,
      significant,
      petImgUrl: imgFiles && (await postImgHandler(imgFiles, 'mypets')),
    });
    setOpen(false);
    nameRef.current!.value = '';
    speciesRef.current!.value = '';
    genderRef.current!.value = '';
    ageRef.current!.value = '';
    significantRef.current!.value = '';
    setImg(
      'https://s3.ap-northeast-2.amazonaws.com/petching.image/dog-5960092_1920.jpg',
    );
  };
  const cancelHandler = () => {
    setOpen(false);
    nameRef.current!.value = '';
    speciesRef.current!.value = '';
    genderRef.current!.value = '';
    ageRef.current!.value = '';
    significantRef.current!.value = '';
    setImg(
      'https://s3.ap-northeast-2.amazonaws.com/petching.image/dog-5960092_1920.jpg',
    );
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
          src={img}
          alt="입력된 이미지"
          className="w-full h-full object-cover"
        />
        <input
          id="attach-file"
          type="file"
          accept="image/*"
          className="hidden"
          onChange={changeImgHandler}
        />
      </label>
      <div className="flex flex-col my-5">
        <label className="flex justify-between w-[280px]">
          <p>이름</p>
          <input className="border" ref={nameRef} />
        </label>
        <label className="flex justify-between">
          <p>종(세부종)</p>
          <input className="border" ref={speciesRef} />
        </label>
        <label className="flex justify-between">
          <p>성별</p>
          <input className="border" ref={genderRef} />
        </label>
        <label className="flex justify-between">
          <p>나이</p>
          <input className="border" type="number" ref={ageRef} />
        </label>
        <label className="flex justify-between">
          <p>특이사항</p>
          <input className="border" ref={significantRef} />
        </label>
      </div>
      <div>
        <button className="mr-5" onClick={cancelHandler} type="button">
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
