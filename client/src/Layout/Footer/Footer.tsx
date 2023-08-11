import { RxGithubLogo } from 'react-icons/rx';
import { useNavigate } from 'react-router-dom';
const Footer = () => {
  const navigate = useNavigate();
  const toInquery = () => {
    navigate('/inquery');
  };
  return (
    <div className="bg-[#99DDCC] h-8 fixed bottom-0 left-0 w-full z-50 flex justify-between items-center px-2">
      <div className="w-5">
        <img
          src="https://img.icons8.com/?size=512&id=121198&format=png"
          alt="발바닥 모양의 아이콘"
        />
      </div>
      <div className="flex items-center">
        <button onClick={toInquery}>문의하기</button>
        <button className="w-5 ml-2">
          <RxGithubLogo />
        </button>
      </div>
    </div>
  );
};

export default Footer;
