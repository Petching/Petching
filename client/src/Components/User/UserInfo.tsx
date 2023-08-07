/* eslint-disable */
import { useState } from 'react';
import { useGetUserProfile } from '../../Hook/useGetUserProfile';
import { AiOutlineUser, AiOutlineHome, AiOutlineMail } from 'react-icons/ai';
import { usePatchUserProfile } from '../../Hook/usePatchUserProfile';
import { useDeleteUserProfile } from '../../Hook/useDeleteUserProfile';
import { useNavigate } from 'react-router-dom';
import { checkNickname } from '../../API/signUp';
import { postImgHandler } from '../../Util/postImg';

const UserInfo: React.FC<{ userId: string }> = ({ userId }) => {
  const navigate = useNavigate();
  const { UserProfile } = useGetUserProfile(userId);
  const [onEdit, setOnEdit] = useState(false);
  const [changeImg, setChangeImg] = useState<string>(
    UserProfile!.profileImgUrl ||
      'https://s3.ap-northeast-2.amazonaws.com/petching.image/dog-5960092_1920.jpg',
  );
  const [isDuplication, setIsDuplication] = useState<boolean>(false);

  const { handlerPatchProfile } = usePatchUserProfile(userId);
  const { handlerDeleteUserProfile } = useDeleteUserProfile(userId);
  const [changeNickName, setChangeNickName] = useState<string>(
    UserProfile!.nickName || '',
  );

  const [changeAddress, setChangeAddress] = useState<string>(
    UserProfile!.address || '',
  );

  const inputChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value, name } = e.currentTarget;
    if (name === 'nickname') {
      setChangeNickName(value);
    } else {
      setChangeAddress(value);
    }
  };

  const changeImgHandler = async (e: React.ChangeEvent<HTMLInputElement>) => {
    const target = e.currentTarget;
    const files = (target.files as FileList)[0];
    if (!files) return;
    const reader = new FileReader();
    reader.readAsDataURL(files);
    const data = await postImgHandler(files, 'profiles');
    setChangeImg(data);
  };

  const nickNameCheckHandler = () => {
    setIsDuplication(true);
  };

  const submitHandler = () => {
    handlerPatchProfile({
      userId,
      nickname: changeNickName,
      address: changeAddress,
      profileImgUrl: changeImg,
      password: '123123',
    });
    setOnEdit(false);
    setChangeNickName(UserProfile!.nickName);
    setChangeAddress(UserProfile!.address || '');
    setChangeImg(UserProfile!.profileImgUrl);
  };

  const deleteHandler = () => {
    handlerDeleteUserProfile(userId);
    navigate('/');
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
            src={UserProfile?.profileImgUrl}
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
              value={changeNickName}
              name="nickname"
              onChange={inputChangeHandler}
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
            <p>{UserProfile?.nickName}</p>
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
              value={changeAddress}
              name="address"
              onChange={inputChangeHandler}
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
            <button
              className="mr-3 text-slate-400 hover:text-red-700"
              onClick={deleteHandler}
            >
              회원 탈퇴
            </button>
            <button
              className="mr-3 hover:text-slate-400"
              onClick={() => setOnEdit(false)}
            >
              수정취소
            </button>
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
