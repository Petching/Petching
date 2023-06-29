import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { RxHamburgerMenu } from 'react-icons/rx';

const GNB = () => {
  const navigate = useNavigate();
  const [isLogin, setIsLogin] = useState<boolean>(false);
  const [userIcon, setUserIcon] = useState<boolean>(false);
  const [menu, setMenu] = useState<boolean>(false);
  const userOpen = () => {
    setUserIcon(prev => !prev);
  };
  const menuOpen = () => {
    setMenu(prev => !prev);
  };
  const toSignIn = () => {
    navigate('/signin');
  };
  const toSignUp = () => {
    navigate('/signup');
  };
  const toCareList = () => {
    navigate('/carelist');
  };
  const tempLogout = () => {
    setIsLogin(false);
    setUserIcon(false);
  };

  return (
    <>
      <div className="absolute right-0 md:right-10 flex justify-center items-center">
        <button className="mx-3 hidden md:block" onClick={toCareList}>
          돌봄리스트
        </button>
        <button className="mx-3 hidden md:block">자랑하기</button>
        {isLogin ? (
          <>
            <button className="mx-3" onClick={userOpen}>
              <img
                src="https://cdn.pixabay.com/photo/2023/06/14/10/02/pied-flycatcher-8062744_640.jpg"
                alt="유저 메뉴 버튼"
                className="w-10 h-10 rounded-full"
              />
            </button>
          </>
        ) : (
          <>
            <button className="mx-3 hidden md:block" onClick={toSignIn}>
              로그인
            </button>
            <button className="mx-3 hidden md:block" onClick={toSignUp}>
              회원가입
            </button>
            <button className="mx-3 block md:hidden" onClick={menuOpen}>
              <RxHamburgerMenu />
            </button>
          </>
        )}
      </div>
      {userIcon && (
        <ul className="bg-slate-400 absolute right-0 top-14 w-40 text-center rounded-b">
          <li className="h-10 leading-10">abc@def.com</li>
          <li className="h-10 leading-10 hover:bg-white block md:hidden">
            <button>돌봄리스트</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white block md:hidden">
            <button>자랑하기</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button>마이 페이지</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button>문의 내역</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button onClick={tempLogout}>로그아웃</button>
          </li>
        </ul>
      )}
      {menu && (
        <ul className="bg-slate-400 absolute right-0 top-14 w-40 text-center rounded-b">
          <li className="h-10 leading-10 hover:bg-white">
            <button>돌봄리스트</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button>자랑하기</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button>로그인</button>
          </li>
          <li className="h-10 leading-10 hover:bg-white">
            <button>회원가입</button>
          </li>
        </ul>
      )}
    </>
  );
};

export default GNB;
