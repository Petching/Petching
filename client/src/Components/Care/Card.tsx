import React from 'react';
import Carousel from './Carousel';
import maltese1 from '../../Style/maltese1.jpg';
const Card = () => {
  return (
    <div className="m-4">
      <div className=" text-center rounded-md w-[18rem] h-[26rem] pt-6 bg-white shadow-lg shadow-gray-400">
        <div className="mx-auto w-60">
          <Carousel />
        </div>
        <div>
          <button className="w-[7rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full mr-1">
            서울시서초구
          </button>
          <button className="w-[5rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full ml-1">
            산책가능
          </button>
        </div>
        <div className="mt-5">정성을 다해 모시겠습니다</div>
        <div className="flex justify-center items-center">
          <img className="w-8 h-8 rounded-full mr-2" src={maltese1} />
          <div className="mr-2">서초구핑크썬구리</div>
          <div className="mr-2">1박 50,000W</div>
        </div>
      </div>
    </div>
  );
};
export default Card;
