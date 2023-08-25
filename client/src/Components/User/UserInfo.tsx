/* eslint-disable */
import { useEffect, useState } from 'react';
import { useGetUserProfile } from '../../Hook/useGetUserProfile';
import {
  AiOutlineUser,
  AiOutlineHome,
  AiOutlineMail,
  AiOutlineKey,
} from 'react-icons/ai';
import { usePatchUserProfile } from '../../Hook/usePatchUserProfile';
import { useDeleteUserProfile } from '../../Hook/useDeleteUserProfile';
import { useNavigate } from 'react-router-dom';
import { checkNickname } from '../../API/signUp';
import { deleteImgHandler, postImgHandler } from '../../Util/postImg';
import { checkPw } from '../../API/user';
import Warning from '../Common/Warning';

const UserInfo: React.FC<{ userId: string }> = ({ userId }) => {
  const navigate = useNavigate();
  const { UserProfile, GetUserProfileError } = useGetUserProfile(userId);
  const [onEdit, setOnEdit] = useState(false);
  const [changeImg, setChangeImg] = useState<string>(
    UserProfile?.profileImgUrl ||
      'https://s3.ap-northeast-2.amazonaws.com/petching.image/dog-5960092_1920.jpg',
  );
  const [imgFiles, setImgFiles] = useState<File>();

  const [changePw, setChangePw] = useState<boolean>(false);
  const [originPw, setOriginPw] = useState<string>('');
  const [newPw, setNewPw] = useState<string>('');
  const [newConfirmPw, setNewConfirmPw] = useState<string>('');

  const [nickCheck, setNickCheck] = useState<boolean>(false);
  const [nickError, setNickError] = useState<string>('');
  const [isPwError, setIsPwError] = useState<boolean>(false);
  const [isPwEqual, setIsPwEqual] = useState<boolean>(false);

  const { handlerPatchProfile } = usePatchUserProfile(userId);
  const { handlerDeleteUserProfile } = useDeleteUserProfile(userId);
  const [changeNickName, setChangeNickName] = useState<string>(
    UserProfile?.nickName || '',
  );

  const [changeAddress, setChangeAddress] = useState<string>(
    UserProfile?.address || '',
  );

  const [onModal, setOnModal] = useState<boolean>(false);

  const onEditHandler = () => {
    // 초기화
    setChangeImg(
      UserProfile?.profileImgUrl ||
        'https://s3.ap-northeast-2.amazonaws.com/petching.image/dog-5960092_1920.jpg',
    );
    setChangeNickName(UserProfile?.nickName || '');
    setChangeAddress(UserProfile?.address || '');
    setOriginPw('');
    setNewPw('');
    setNewConfirmPw('');
    setNickError('');
    setOnEdit(true);
  };

  const inputChangeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setIsPwError(false);
    setIsPwEqual(false);
    const { value, name } = e.currentTarget;
    switch (name) {
      case 'nickname':
        setChangeNickName(value);
        setNickCheck(false);
        setNickError('');
        return;
      case 'address':
        return setChangeAddress(value);
      case 'originPw':
        return setOriginPw(value);
      case 'newPw':
        return setNewPw(value);
      case 'newConfirmPw':
        return setNewConfirmPw(value);
      default:
        return;
    }
  };

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

  const nickNameCheckHandler = async () => {
    if (UserProfile?.nickName === changeNickName) {
      setNickCheck(true);
      return;
    }
    const data = await checkNickname(changeNickName);
    if (data) {
      setNickError('중복된 닉네임입니다.');
      return;
    } else {
      setNickError('사용할 수 있습니다.');
      setNickCheck(true);
    }
  };

  const submitHandler = async () => {
    if (!nickCheck) {
      setNickError('중복확인을 진행해주세요.');
      return;
    }
    // 비밀번호 변경이 일어날 경우
    if (changePw) {
      const data = await checkPw(originPw);
      // 기존 비밀번호가 일치하지 않을 경우 실행
      if (!data) {
        setIsPwError(true);
        return;
      }
      // 변경할 비밀번호가 일치하지 않을 경우 실행
      if (newPw !== newConfirmPw) {
        setIsPwEqual(true);
        return;
      }
    }
    // 이미지 변경이 일어났다면 기존 이미지 삭제도 실행
    if (imgFiles) {
      deleteImgHandler(UserProfile!.profileImgUrl, 'profiles');
    }

    handlerPatchProfile({
      userId,
      nickName: changeNickName,
      address: changeAddress,
      profileImgUrl: imgFiles
        ? await postImgHandler(imgFiles, 'profiles')
        : UserProfile?.profileImgUrl,
      password: changePw ? newPw : undefined,
    });
    setChangeNickName(UserProfile!.nickName);
    setChangeAddress(UserProfile!.address || '');
    setChangeImg(UserProfile!.profileImgUrl);
    setNickCheck(false);
    setNickError('');
    setOnEdit(false);
    setChangePw(false);
  };

  const cancelHandler = () => {
    setOnEdit(false);
    setChangeNickName(UserProfile!.nickName);
    setChangeAddress(UserProfile!.address || '');
    setChangeImg(UserProfile!.profileImgUrl);
    setIsPwError(false);
    setIsPwEqual(false);
    setNickCheck(false);
    setNickError('');
    setChangePw(false);
  };

  const deleteHandler = () => {
    setOnModal(false);
    // 해당 훅에서 토큰 삭제, logout 처리 실행
    handlerDeleteUserProfile(userId);
    navigate('/');
    deleteImgHandler(UserProfile!.profileImgUrl, 'profiles');
  };

  // 수정 버튼을 클릭한 채로 다른 유저의 페이지로 이동할 경우, 수정 칸이 열려있는 현상 방지
  useEffect(() => {
    setOnEdit(false);
  }, [userId]);

  if (GetUserProfileError) {
    return (
      <div>
        <h1>존재하지 않는 유저입니다.</h1>
      </div>
    );
  }
  return (
    <div className="flex items-center w-9/12 my-10 relative flex-col sm:flex-row">
      <div className="mr-6 w-32 h-32 box-border my-5">
        {onEdit ? (
          <label className="w-32 h-32 rounded overflow-hidden border relative block cursor-pointer hover:border-4 box-border">
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
            src={UserProfile?.profileImgUrl}
            alt="유저의 프로필 이미지"
            className="w-32 h-32 rounded object-cover"
          />
        )}
      </div>
      <div className="w-10/12 flex">
        <div className="w-5/12 relative">
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
                className="ml-2 px-2 flex-2 rounded hover:scale-90 transition-all bg-customGreen hover:bg-customHoverGreen"
                onClick={nickNameCheckHandler}
              >
                중복확인
              </button>
              <p className="ml-2 text-red-700">{nickError}</p>
              {/* {isDuplication && (

              )} */}
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
          {UserProfile?.userDivision && (
            <>
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
            </>
          )}
          {onEdit && UserProfile?.socialType === 'null' && (
            <label className="flex items-center absolute top-8 right-10">
              <p className="mr-2">비밀번호 변경</p>
              <input
                type="checkbox"
                id="secret"
                checked={changePw}
                onChange={() => setChangePw(prev => !prev)}
              />
            </label>
          )}
        </div>
        {changePw && (
          <div className="w-6/12">
            <label className="flex items-center">
              <p className="text-left text-gray-500 mr-6">기존 비밀번호</p>
              <input
                placeholder="기존 비밀번호"
                className="border border-gray-300 rounded mr-2 block"
                value={originPw}
                name="originPw"
                onChange={inputChangeHandler}
              />
              {isPwError && (
                <p className="ml-2 text-red-700">
                  비밀번호를 다시 확인해주세요.
                </p>
              )}
            </label>
            <label className="flex items-center">
              <p className="text-left text-gray-500 mr-9">새 비밀번호</p>
              <input
                placeholder="새로운 비밀번호"
                className="border border-gray-300 rounded mr-2 block"
                value={newPw}
                name="newPw"
                onChange={inputChangeHandler}
              />
            </label>
            <label className="flex items-center">
              <p className="text-left text-gray-500 mr-2">새 비밀번호 확인</p>
              <input
                placeholder="새로운 비밀번호 확인"
                className="border border-gray-300 rounded mr-2 block"
                value={newConfirmPw}
                name="newConfirmPw"
                onChange={inputChangeHandler}
              />
              {isPwEqual && (
                <p className="ml-2 text-red-700">
                  동일한 비밀번호를 입력해주세요.
                </p>
              )}
            </label>
          </div>
        )}
      </div>
      <div className="absolute right-0 bottom-0">
        {onEdit ? (
          <>
            <button
              className="mr-3 text-slate-400 hover:text-red-700"
              onClick={() => setOnModal(true)}
            >
              회원 탈퇴
            </button>
            <button
              className="mr-3 hover:text-slate-400"
              onClick={cancelHandler}
            >
              수정취소
            </button>
            <button onClick={submitHandler} className="hover:text-hoverGreen">
              수정 완료
            </button>
          </>
        ) : (
          UserProfile?.userDivision && (
            <button onClick={onEditHandler} className="hover:text-hoverGreen">
              회원 정보 수정
            </button>
          )
        )}
      </div>
      {onModal && (
        <Warning
          title="회원 탈퇴"
          sub="사이트를 탈퇴합니다"
          fn={deleteHandler}
          setState={setOnModal}
        />
      )}
    </div>
  );
};

export default UserInfo;
