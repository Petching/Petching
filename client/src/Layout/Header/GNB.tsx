import { MouseEvent, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { RxHamburgerMenu } from 'react-icons/rx';
import useLoginStore from '../../store/login';
import { getUserIdFromToken } from '../../Util/getUserIdFromToken';
import { useGetUserProfile } from '../../Hook/useGetUserProfile';

const GNB = () => {
  const navigate = useNavigate();
  const [userIcon, setUserIcon] = useState<boolean>(false);
  const [menu, setMenu] = useState<boolean>(false);
  const { isLogin, setLogin, setLogout } = useLoginStore();
  const token = localStorage.getItem('ACCESS_TOKEN');

  const userId = getUserIdFromToken(isLogin, token);
  const { UserProfile } = useGetUserProfile(`${userId}`);

  useEffect(() => {
    if (token) {
      setLogin();
    } else {
      setLogout();
    }
  }, [isLogin]);

  const userOpen = () => {
    setUserIcon(prev => !prev);
  };
  const menuOpen = () => {
    setMenu(prev => !prev);
  };
  const moveHandler = (e: MouseEvent<HTMLButtonElement>) => {
    const { name } = e.currentTarget;
    switch (name) {
      case 'mypage':
        return navigate(`/user/${userId}`);
      default:
        return navigate(`/${name}`);
    }
  };
  const logoutHandler = () => {
    localStorage.removeItem('ACCESS_TOKEN');
    setLogout();
    navigate('/');
    setUserIcon(false);
  };

  return (
    <>
      <div className="absolute right-0 md:right-10 flex justify-center items-center">
        <button
          className="mx-3 hidden md:block hover:text-customGreen"
          name="carelist"
          onClick={moveHandler}
        >
          돌봄리스트
        </button>
        <button
          className="mx-3 hidden md:block hover:text-customGreen"
          name="peacock"
          onClick={moveHandler}
        >
          자랑하기
        </button>
        {isLogin ? (
          <>
            <button className="mx-3" onClick={userOpen}>
              {UserProfile && (
                <img
                  src={UserProfile!.profileImgUrl}
                  alt="유저 메뉴 버튼"
                  className="w-10 h-10 rounded-full"
                />
              )}
            </button>
          </>
        ) : (
          <>
            <button
              className="mx-3 hidden md:block hover:text-customGreen"
              name="signin"
              onClick={moveHandler}
            >
              로그인
            </button>
            <button
              className="mx-3 hidden md:block hover:text-customGreen"
              name="signup"
              onClick={moveHandler}
            >
              회원가입
            </button>
            <button className="mx-3 block md:hidden" onClick={menuOpen}>
              <RxHamburgerMenu />
            </button>
          </>
        )}
      </div>
      {userIcon && (
        <ul className="bg-slate-400 absolute right-0 top-[4.5rem] w-40 text-center rounded-b">
          <li className="h-10 leading-10">{UserProfile?.email}</li>
          <li className="h-10 leading-10 hover:bg-white block md:hidden">
            <button
              className="w-full h-full"
              name="carelist"
              onClick={moveHandler}
            >
              돌봄리스트
            </button>
          </li>
          <li className="h-10 leading-10 hover:bg-white block md:hidden">
            <button
              className="w-full h-full"
              name="peacock"
              onClick={moveHandler}
            >
              자랑하기
            </button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button
              className="w-full h-full"
              name="mypage"
              onClick={moveHandler}
            >
              마이 페이지
            </button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button className="w-full h-full">문의 내역</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button className="w-full h-full" onClick={logoutHandler}>
              로그아웃
            </button>
          </li>
        </ul>
      )}
      {menu && (
        <ul className="bg-slate-400 absolute right-0 top-14 w-40 text-center rounded-b">
          <li className="h-10 leading-10 hover:bg-white">
            <button className="w-full h-full">돌봄리스트</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button className="w-full h-full">자랑하기</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button className="w-full h-full">로그인</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button className="w-full h-full">회원가입</button>
          </li>
        </ul>
      )}
    </>
  );
};

export default GNB;
