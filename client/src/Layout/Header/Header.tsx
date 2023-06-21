import { useNavigate } from 'react-router-dom';
import GNB from './GNB';

const Header = () => {
  const navigate = useNavigate();
  const toMain = () => {
    navigate('/');
  };

  return (
    <div className='bg-customGreen h-16 fixed top-0 left-0 w-full flex justify-center items-center z-50'>
      <div
        className='flex w-24 h-10 justify-between cursor-pointer'
        onClick={toMain}
      >
        <div className='w-10 hover:scale-90'>
          <img
            src='https://img.icons8.com/?size=512&id=121198&format=png'
            alt='발바닥 모양의 아이콘'
          />
        </div>
        <h1 className='text-3xl'>펫칭</h1>
      </div>
      <GNB />
    </div>
  );
};

export default Header;
