import { useNavigate } from 'react-router-dom';
import { useGetMainData } from '../../Hook/useGetMainData';

const MainPeacock = () => {
  const { MainData } = useGetMainData();
  const navigate = useNavigate();
  const toPeacock = () => {
    navigate('/peacock');
  };

  return (
    <div className="w-full h-full flex flex-col justify-center items-center md:flex-row">
      <div className="w-1/5 -mt-20">
        <p className="text-center text-xl">
          당신의 반려동물을 <br />
          자랑하고 <br />
          다른 사람들의 반려동물을 <br />
          구경하세요
        </p>
        <button
          className="w-full h-16 mt-10 bg-customPink text-xl hover:scale-90 transition-all rounded"
          onClick={toPeacock}
        >
          자랑하러 가기
        </button>
      </div>
      <div className="flex ml-32">
        {MainData &&
          MainData.ImgUrls.map((img, idx) => (
            <div
              className={`w-10 h-10 md:w-60 md:h-60 relative ${
                idx % 2 === 0 ? '-top-10' : 'top-10'
              } rounded overflow-hidden hover:scale-105 transition-all`}
              key={idx}
            >
              <img
                loading="lazy"
                src={`${img}`}
                className="w-full h-full object-cover"
              />
            </div>
          ))} */}
      </div>
    </div>
  );
};

export default MainPeacock;
