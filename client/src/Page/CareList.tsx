import React from 'react';
import Card from '../Components/Care/Card';
import Draft from '../Components/Care/TextEditor';

const CareList = () => {
  return (
    <div>
      <div className="flex flex-col items-center bg-gray-100 mx-20">
        <div className="flex">
          <div className="mr-4">
            <div>어느 지역을 찾으시나요?</div>
            <input placeholder="동 이름을 검색하세요"></input>
          </div>
          <div>
            <div>언제 맡기시나요?</div>
            <input type="date"></input>
          </div>
        </div>
        <div className="w-[15rem]">
          <div className="text-center">원하시는 조건을 선택하세요</div>
          <div className="flex justify-center">
            <button className="w-[7rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full mr-1">
              서울시서초구
            </button>
            <button className="w-[7rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full mr-1">
              대형견 가능
            </button>
            <button className="w-[7rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full mr-1">
              픽업 가능
            </button>
          </div>
          <div className="flex justify-end">
            <button className="w-[7rem] h-7 bg-customGreen shadow-sm shadow-gray-400 rounded-full mr-1">
              검색
            </button>
            <button className="w-[7rem] h-7 bg-customGreen shadow-sm shadow-gray-400 rounded-full mr-1">
              글작성
            </button>
          </div>
        </div>
      </div>
      <div className="flex flex-wrap justify-center">
        <Card />
        <Card />
        <Card />
        <Card />
        <Card />
        <Card />
      </div>
    </div>
  );
};

export default CareList;
