import { useNavigate } from 'react-router-dom';
import icon_Pet from '../../Style/icon_Pet.png';
import GNB from './GNB';

const Header = () => {
  const navigate = useNavigate();
  const toMain = () => {
    navigate('/');
  };

  return (
    <div className="bg-white h-[4.5rem] fixed top-0 left-0 w-full flex justify-between items-center z-50 border text-xl">
      <div
        className="flex w-24 h-10 justify-evenly cursor-pointer items-center ml-10"
        onClick={toMain}
      >
        <div className="w-9 hover:scale-90 overflow-hidden rounded-full mr-2">
          <img src={icon_Pet} alt="발바닥 모양의 아이콘" />
        </div>
        <h1 className="text-3xl text-hoverGreen">펫칭</h1>
      </div>
      <GNB />
    </div>
  );
};

export default Header;
