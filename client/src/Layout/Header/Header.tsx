import { useNavigate } from 'react-router-dom';
import logo from '../../Style/petching_logo.png';
import GNB from './GNB';

const Header = () => {
  const navigate = useNavigate();
  const toMain = () => {
    navigate('/');
  };

  return (
    <div className="bg-white h-[4.5rem] fixed top-0 left-0 w-full flex justify-between items-center z-50 border text-xl">
      <img
        src={logo}
        className="flex w-[6.1rem] justify-evenly cursor-pointer items-center ml-10"
        onClick={toMain}
      ></img>
      <GNB />
    </div>
  );
};

export default Header;
