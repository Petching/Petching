/* eslint-disable */
import React, { useState } from 'react';
import Board from '../Components/Care/Board';
import Card from '../Components/Care/Card';
import TextEditor from '../Components/Care/TextEditor';
import ReactCalendar from '../Components/Care/ReactCalendar';
import Postcode from '../Components/Care/Postcode';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

interface Date {
  year: number;
  month: number;
  day: number;
}

const CareList = () => {
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [locationTag, setLocationTag] = useState('');
  const navigate = useNavigate();

  const onDateSelected = (value: any) => {
    if (value.from && value.to) {
      setStartDate({ year: value.from.year, month: value.from.month, day: value.from.day });
      setEndDate({ year: value.to.year, month: value.to.month, day: value.to.day });
    }
  };
  const onAddressSelected = (address: string) => {
    setLocationTag(address);
  }

  const handleSubmit = async() => {
    if(startDate && endDate && locationTag){
      try{
        const response = await axios.get("https://server.petching.net/careposts/search",{
          params:{
            locationTag,
            'startDate.day':startDate.day,
            'startDate.month':startDate.month,
            'startDate.year':startDate.year,
            'endDate.day':endDate.day,
            'endDate.month':endDate.month,
            'endDate.year':endDate.year,
          },
        });
        console.log(response.data);
      }catch(error){
        console.error(error);
      }
    }
  };

  const toCareListDetail = () => {
    navigate('/carelistdetail');
  };
  return (
    <div className="text-[1.2rem]">
      <div className="flex flex-col w-full items-center bg-gray-100 ">
      <div> 
      <div className="flex flex-col items-center">
            <div className="text-center leading-10 ">어느 지역을 찾으시나요?</div>
            <div className="flex items-center">
              <div>
              <Postcode onAddressSelected={onAddressSelected}/>
              </div>
            </div>
          </div>
          <div className="flex flex-col items-center"> 
            <div className="text-center leading-10">언제 맡기시나요?</div>
            <div className="flex items-center"> 
            <div>
            <ReactCalendar onDateSelected={onDateSelected}/>
            </div>
            
          </div>
          </div>
        </div>
        <div className="w-[15rem]">
          <div className="text-center leading-10">
            원하시는 조건을 선택하세요
          </div>
          <div className="flex justify-center mb-7">
            <button className="w-[10rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full mr-1">
              서울시
            </button>
            <button className="w-[10rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full mr-1">
              대형견
            </button>
            <button className="w-[10rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full mr-1">
              픽업 가능
            </button>
          </div>
          <div className="flex justify-end mb-7">
            <button className="w-[7rem] h-7 bg-customGreen shadow-sm shadow-gray-400 rounded-full mr-1" onClick={handleSubmit}>
              검색
            </button>
            <button className="w-[7rem] h-7 bg-customGreen shadow-sm shadow-gray-400 rounded-full mr-1" onClick={toCareListDetail}>
              글작성
            </button>
          </div>
        </div>
      </div>
      <div className="flex flex-wrap justify-center">
        <Card />
        <Card />
      </div>

    </div>
  );
};

export default CareList;
