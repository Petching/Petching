import { useGetMainData } from '../../Hook/useGetMainData';

const MainPeacock = () => {
  const { MainData } = useGetMainData();

  return (
    <div className="w-full h-full flex flex-col justify-center items-center md:flex-row">
      <div className="w-1/5 -mt-20">
        <p className="text-center text-xl">
          당신의 반려동물을 <br />
          자랑하고 <br />
          다른 사람들의 반려동물을 <br />
          구경하세요
        </p>
        <button className="w-full h-16 mt-10 bg-customPink text-xl hover:scale-90 transition-all rounded">
          자랑하러 가기
        </button>
      </div>
      <div className="flex">
        {MainData && (
          <>
            <div className="w-10 h-10 md:w-60 md:h-60 relative -top-10 left-32 rounded overflow-hidden hover:scale-105 transition-all">
              <img
                loading="lazy"
                src={`${MainData[0]}`}
                className="w-full h-full object-cover"
              />
            </div>
            <div className="w-10 h-10 md:w-60 md:h-60 relative top-10 left-20 rounded overflow-hidden hover:scale-105 transition-all">
              <img
                loading="lazy"
                src={`${MainData[1]}`}
                className="w-full h-full object-cover"
              />
            </div>
            <div className="w-10 h-10 md:w-60 md:h-60 relative -top-10 left-10 rounded overflow-hidden hover:scale-105 transition-all">
              <img
                loading="lazy"
                src={`${MainData[2]}`}
                className="w-full h-full object-cover"
              />
            </div>
            <div className="w-10 h-10 md:w-60 md:h-60 relative top-10 rounded overflow-hidden hover:scale-105 transition-all">
              <img
                loading="lazy"
                src={`${MainData[3]}`}
                className="w-full h-full object-cover"
              />
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default MainPeacock;
