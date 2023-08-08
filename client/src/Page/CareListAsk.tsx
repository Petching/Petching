import Carousel from '../Components/Care/Carousel';

const CareListDetail = () => {
  return (
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen text-xl">
      <div>
        <Carousel></Carousel>
      </div>
      <div className="bg-white">
        <div>서초구핑크썬구리</div>
        <div>정성을 다해 모시겠습니다</div>
        <button className="bg-customGreen rounded-full">서울시 서초구</button>
        <button className="bg-customGreen rounded-full">산책 가능</button>
      </div>
      <div className="bg-white mt-5">
        <div>맡겨만 주십시오</div>
      </div>
    </div>
  );
};

export default CareListDetail;
