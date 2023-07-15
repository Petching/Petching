import { useState } from 'react';
import { useDeleteMyPet } from '../../Hook/useDeleteMyPet';
import { MyPetsType } from './MyPets';
import { usePatchMyPet } from '../../Hook/usePatchMyPet';

const PetCard: React.FC<MyPetsType> = ({
  // img,
  name,
  kind,
  gender,
  age,
  weight,
  vaccination,
  etc,
}) => {
  const { handlerPatchMyPet } = usePatchMyPet();
  const { handlerDeleteMyPet } = useDeleteMyPet();
  const [onEdit, setOnEdit] = useState<boolean>(false);
  const [changeImg, setChangeImg] = useState<string>(
    'https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_1280.jpg',
  );
  const [changeName, setChangeName] = useState<string>(name);
  const [changeKind, setChangeKind] = useState<string>(kind);
  const [changeGender, setChangeGender] = useState<string>(gender);
  const [changeAge, setChangeAge] = useState<string>(age);
  const [changeWeight, setChangeWeight] = useState<string>(weight || '');
  const [changeVaccination, setChangeVaccination] = useState<string>(
    vaccination || '',
  );
  const [changeEtc, setChangeEtc] = useState<string>(etc || '');

  const changeImgHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const target = e.currentTarget;
    const files = (target.files as FileList)[0];
    if (!files) return;
    const reader = new FileReader();
    reader.onloadend = () => {
      setChangeImg(reader.result as string);
    };
    reader.readAsDataURL(files);
  };

  const changeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value, name } = e.currentTarget;
    switch (name) {
      case 'name':
        return setChangeName(value);
      case 'kind':
        return setChangeKind(value);
      case 'age':
        return setChangeAge(value);
      case 'gender':
        return setChangeGender(value);
      case 'weight':
        return setChangeWeight(value);
      case 'vaccination':
        return setChangeVaccination(value);
      case 'etc':
        return setChangeEtc(value);
      default:
        return;
    }
  };

  const submitHandler = () => {
    handlerPatchMyPet({
      name: changeName,
      kind: changeKind,
      gender: changeGender,
      age: changeAge,
      weight: changeWeight,
      vaccination: changeVaccination,
      etc: changeEtc,
    });
    setOnEdit(false);
  };

  const deleteHandler = () => {
    handlerDeleteMyPet();
  };
  return (
    <div className="flex border p-4 rounded relative">
      <div className="w-32 h-32 rounded overflow-hidden mr-3">
        {onEdit ? (
          <label className="w-32 h-32 rounded overflow-hidden border relative block cursor-pointer hover:border-4">
            <div className="absolute bottom-0 left-0 w-full h-1/2 bg-white text-center p-2 opacity-80">
              이미지 <br />
              변경
            </div>
            <img
              src={changeImg}
              alt="변경된 이미지"
              className="w-full h-full"
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
            src="https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_1280.jpg"
            alt="반려동물의 이미지"
            className="w-32 h-32 rounded"
          />
        )}
      </div>
      <form>
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
              value={changeKind}
              name="kind"
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{kind}</p>
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
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{age}</p>
          )}
        </label>
        <label className="flex flex-col">
          <p className="text-left text-gray-500 text-xs">몸무게</p>
          {onEdit ? (
            <input
              value={changeWeight}
              name="weight"
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{weight}</p>
          )}
        </label>
        <label className="flex flex-col">
          <p className="text-left text-gray-500 text-xs">백신여부</p>
          {onEdit ? (
            <input
              value={changeVaccination}
              name="vaccination"
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{vaccination}</p>
          )}
        </label>
        <label className="flex flex-col">
          <p className="text-left text-gray-500 text-xs">그외사항</p>
          {onEdit ? (
            <input
              value={changeEtc}
              name="etc"
              onChange={changeHandler}
              className="border border-gray-300 rounded mr-2 block"
            />
          ) : (
            <p>{etc}</p>
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
          onClick={deleteHandler}
        >
          삭제
        </button>
      </div>
    </div>
  );
};

export default PetCard;
