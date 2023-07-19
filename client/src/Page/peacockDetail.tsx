/* eslint-disable */
import { CarouselComponent, Square } from '../Components/peacockComponent';
import doctors from '../Style/doctors.jpg';
import { LikeComponent } from '../Components/peacockComponent';
const PeacockDetail = () => {
  return (
    <div className="flex items-center justify-center h-full mb-12">
      <div className="mt-20 w-5/6 bg-[#f2f2f2] flex flex-col items-center ">
        <div className="mt-12 w-2/3">
          <CarouselComponent />
        </div>
        <div className="w-5/6 h-32 mt-12 flex items-center rounded-2xl bg-[#ffffff]">
          <img className="ml-8 w-20 h-20 rounded-full" src={doctors}></img>
          <div className="h-32 ml-8 flex flex-col items-start">
            <div className="mt-4 text-xs">애플망고</div>
            <div className="mt-2 mb-2 font-bold text-3xl">도와조약입니다</div>
            <LikeComponent />
          </div>
        </div>
        <div className="w-5/6 h-64 mt-12 rounded-2xl bg-[#ffffff]">
          <div className="ml-8 mt-8"> 도와조약에 이은 도와조펫입니다</div>
        </div>
        <div className="w-5/6 h-20 mt-12 mb-8 flex items-center rounded-2xl bg-[#ffffff]">
          <img className="ml-8 w-12 h-12 rounded-full" src={doctors}></img>
          <span className="ml-4 font-bold text-2xl">염도둑과경찰</span>
          <div className="ml-8"> 펫칭입니다</div>
        </div>
      </div>
    </div>
  );
};

export default PeacockDetail;
