import { useState } from 'react';
import { useDeleteMyPet } from '../../Hook/useDeleteMyPet';
import { MyPetsType } from './MyPets';
import { usePatchMyPet } from '../../Hook/usePatchMyPet';
import { deleteImgHandler, postImgHandler } from '../../Util/postImg';
import Warning from '../Common/Warning';

const PetCard: React.FC<MyPetsType> = ({
  name,
  species,
  gender,
  age,
  petImgUrl,
  significant,
  myPetId,
  userId,
}) => {
  const { handlerPatchMyPet } = usePatchMyPet(userId!);
  const { handlerDeleteMyPet } = useDeleteMyPet(myPetId!, userId!);
  const [onEdit, setOnEdit] = useState<boolean>(false);
  const [changeImg, setChangeImg] = useState<string>(petImgUrl);
  const [changeName, setChangeName] = useState<string>(name);
  const [changeSpecies, setChangeSpecies] = useState<string>(species);
  const [changeGender, setChangeGender] = useState<string>(gender);
  const [changeAge, setChangeAge] = useState<number>(age);
  const [changeSignificant, setChangeSignificant] =
    useState<string>(significant);
  const [imgFiles, setImgFiles] = useState<File>();
  const [onModal, setOnModal] = useState<boolean>(false);

  const changeImgHandler = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const target = e.currentTarget;
    const files = (target.files as FileList)[0];
    if (!files) return;
    const reader = new FileReader();
    reader.readAsDataURL(files);
    reader.onloadend = () => {
      setChangeImg(reader.result as string);
    };
    setImgFiles(files);
  };

  const changeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value, name } = e.currentTarget;
    switch (name) {
      case 'name':
        return setChangeName(value);
      case 'species':
        return setChangeSpecies(value);
      case 'age':
        return setChangeAge(Number(value));
      case 'gender':
        return setChangeGender(value);
      case 'significant':
        return setChangeSignificant(value);
      default:
        return;
    }
  };

  const submitHandler = async () => {
    if (imgFiles) {
      deleteImgHandler(petImgUrl, 'mypets');
    }
    handlerPatchMyPet({
      name: changeName,
      species: changeSpecies,
      gender: changeGender,
      age: changeAge,
      petImgUrl: imgFiles
        ? await postImgHandler(imgFiles, 'mypets')
        : petImgUrl,
      significant: changeSignificant,
      myPetId: myPetId,
    });
    setOnEdit(false);
  };

  const deleteHandler = () => {
    setOnModal(false);
    deleteImgHandler(petImgUrl, 'mypets');
    handlerDeleteMyPet();
  };
  return (
    <div className="flex flex-col items-center border p-4 rounded relative sm:flex-row sm:items-start">
      <div className="w-32 h-32 rounded overflow-hidden sm:mr-3 my-10 sm:my-0">
        {onEdit ? (
          <label className="w-32 h-32 rounded overflow-hidden border relative block cursor-pointer hover:border-4">
            <div className="absolute bottom-0 left-0 w-full h-1/2 bg-white text-center p-2 opacity-80">
              이미지 <br />
              변경
            </div>
            <img
              src={changeImg}
              alt="변경된 이미지"
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
        ) : (
          <img
            src={petImgUrl}
            alt="반려동물의 이미지"
            className="w-32 h-32 rounded object-cover"
          />
        )}
      </div>
      <form className="w-2/3">
        <label className="flex flex-col">
          <p className="text-left text-gray-500 text-xs">이름</p>
          {onEdit ? (
            <input
              value={changeName}
              name="name"
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{name}</p>
          )}
        </label>
        <label className="flex flex-col">
          <p className="text-left text-gray-500 text-xs">종</p>
          {onEdit ? (
            <input
              value={changeSpecies}
              name="species"
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{species}</p>
          )}
        </label>
        <label className="flex flex-col">
          <p className="text-left text-gray-500 text-xs">성별</p>
          {onEdit ? (
            <input
              value={changeGender}
              name="gender"
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{gender}</p>
          )}
        </label>
        <label className="flex flex-col">
          <p className="text-left text-gray-500 text-xs">나이</p>
          {onEdit ? (
            <input
              value={changeAge}
              name="age"
              type="number"
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{age}</p>
          )}
        </label>
        <label className="flex flex-col">
          <p className="text-left text-gray-500 text-xs">그외사항</p>
          {onEdit ? (
            <input
              value={changeSignificant}
              name="significant"
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{significant}</p>
          )}
        </label>
      </form>
      <div className="absolute top-4 right-4">
        {onEdit && (
          <button
            className="mr-2 hover:text-slate-400"
            onClick={() => {
              setOnEdit(false);
            }}
          >
            수정취소
          </button>
        )}
        {onEdit ? (
          <button
            className="mr-2 hover:text-customGreen"
            onClick={submitHandler}
          >
            수정 완료
          </button>
        ) : (
          <button
            className="mr-2 hover:text-customGreen"
            onClick={() => {
              setOnEdit(true);
            }}
          >
            수정
          </button>
        )}
        <button
          className="mr-3 text-slate-400 hover:text-red-700"
          onClick={() => setOnModal(true)}
        >
          삭제
        </button>
      </div>
      {onModal && (
        <Warning
          title="펫 카드 삭제"
          sub={`${name} 카드를 삭제하시겠습니까?`}
          fn={deleteHandler}
          setState={setOnModal}
        />
      )}
    </div>
  );
};

export default PetCard;
