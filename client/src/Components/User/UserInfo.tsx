import { useRef, useState } from 'react';
import { useGetUserProfile } from '../../Hook/useGetUserProfile';
import { AiOutlineUser, AiOutlineHome, AiOutlineMail } from 'react-icons/ai';

const UserInfo: React.FC<{ userId: string }> = ({ userId }) => {
  const [onEdit, setOnEdit] = useState(false);
  const [changeImg, setChangeImg] = useState<string>(
    'https://cdn.pixabay.com/photo/2023/06/14/10/02/pied-flycatcher-8062744_640.jpg',
  );
  const nicknameRef = useRef<HTMLInputElement>(null);
  const addressRef = useRef<HTMLInputElement>(null);
  const [isDuplication, setIsDuplication] = useState<boolean>(false);

  const { UserProfile } = useGetUserProfile(userId);

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

  const nickNameCheckHandler = () => {
    const { value } = nicknameRef.current!;
    console.log(value);
    setIsDuplication(true);
  };

  const submitHandler = () => {
    const { value: nickname } = nicknameRef.current!;
    const { value: address } = addressRef.current!;
    console.log(nickname, address);
    setOnEdit(false);
  };

  return (
    <div className="flex items-center w-9/12 my-10 relative">
      <div className="mr-6">
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
            src={UserProfile?.img}
            alt="유저의 프로필 이미지"
            className="w-32 h-32 rounded"
          />
        )}
      </div>
      <div>
        {onEdit ? (
          <label className="flex items-center">
            <p className="text-left text-gray-500 mr-2">
              <AiOutlineUser />
            </p>
            <input
              placeholder="유저 이름"
              className="border border-gray-300 rounded mr-2 block"
              ref={nicknameRef}
            />
            <button
              className="ml-2 px-2 flex-2 rounded hover:scale-90 transition-all bg-customPink hover:bg-customGreen"
              onClick={nickNameCheckHandler}
            >
              중복확인
            </button>
            {isDuplication && (
              <p className="ml-2 text-red-700">중복된 닉네임입니다.</p>
            )}
          </label>
        ) : (
          <label className="flex items-center">
            <p className="text-left text-gray-500 mr-2">
              <AiOutlineUser />
            </p>
            <p>{UserProfile?.name || 'ABC'}</p>
          </label>
        )}
        {onEdit ? (
          <label className="flex items-center">
            <p className="text-left text-gray-500 mr-2">
              <AiOutlineHome />
            </p>
            <input
              placeholder="주소"
              className="border border-gray-300 rounded mr-2 block"
              ref={addressRef}
            />
          </label>
        ) : (
          <label className="flex items-center">
            <p className="text-left text-gray-500 mr-2">
              <AiOutlineHome />
            </p>
            <p>{UserProfile?.address || '유저 주소'}</p>
          </label>
        )}
        {onEdit ? (
          <label className="flex items-center">
            <p className="text-left text-gray-500 mr-2">
              <AiOutlineMail />
            </p>
            <p>이메일은 수정이 불가능합니다.</p>
          </label>
        ) : (
          <label className="flex items-center">
            <p className="text-left text-gray-500 mr-2">
              <AiOutlineMail />
            </p>
            <p>{UserProfile?.email || '유저 email'}</p>
          </label>
        )}
      </div>
      <div className="absolute right-0 bottom-0">
        {onEdit ? (
          <>
            <button className="mr-3 text-slate-400 hover:text-red-700">
              회원 탈퇴
            </button>
            <button className="mr-3 hover:text-slate-400">수정취소</button>
            <button onClick={submitHandler} className="hover:text-customGreen">
              수정 완료
            </button>
          </>
        ) : (
          <button
            onClick={() => setOnEdit(true)}
            className="hover:text-customGreen"
          >
            회원 정보 수정
          </button>
        )}
      </div>
    </div>
  );
};

export default UserInfo;
