import React from 'react';
import cat from '../Style/greencat.png';
import questionPicture from '../Style/question.png';
import InqueryComponent from '../Components/Inquery/InqueryComponent';
import QuestionSection from '../Components/Inquery/QuestionSection';
import { useNavigate } from 'react-router-dom';
import { GoToFunction } from '../Util/types';
import { dummyData } from '../Components/Inquery/dummyData';
import SearchInput from '../Components/Inquery/SearchInput';
import { useState } from 'react';
import inquerydog from '../Style/inquerydog.png';
import mainimg from '../Style/silver.png';

const InqueryPopular = () => {
  const navi = useNavigate();
  const goTo: GoToFunction = (path: string) => {
    navi(path);
  };
  const [search, setSearch] = useState('');
  // dummyData 배열을 조회수를 기준으로 내림차순으로 정렬
  const sortedData = [...dummyData].sort((a, b) => b.조회수 - a.조회수);

  // 상위 10개의 데이터만 가져오기
  const top10Data = sortedData.slice(0, 10);

  return (
    <>
      <div className="relative flex justify-stretch flex-col lg:flex-row items-center w-full h-[60vh] sm:h-[60vh] md:h-[70vh] bg-InqueryMain">
        <div
          className="absolute inset-0 w-full h-full bg-cover bg-center"
          style={{
            backgroundImage: `url(${mainimg})`,
            filter: 'blur(1px)',
            backgroundRepeat: 'no-repeat',
          }}
        ></div>

        <div className="flex-1 flex flex-col p-10 z-10">
          <div className="text-[26px] sm:text-[30px] md:text-[47px] mb-4 p-5 ">
            <div className="flex justify-center items-center">
              안녕하세요.
              <br /> 무엇을 도와드릴까요?
              <img src={inquerydog} className="w-40 h-40" />
            </div>
          </div>
          <div className="flex justify-center items-center">
            <SearchInput search={setSearch} />
          </div>
        </div>
        <div className="flex-1 flex justify-center items-center ml-5 mr-5">
          <QuestionSection goTo={goTo} />
        </div>
      </div>
      <div className="flex justify-center items-center text-2xl mt-4 text-gray-400">
        조회수 상위 10개의 질문만 표시됩니다
      </div>
      <section className="flex flex-col items-center justify-center bg-#e0e0e0">
        <InqueryComponent data={top10Data} search={search} />
      </section>
      <section className="flex justify-left h-[20vh] bg-gray-100 p-10 overflow-hidden">
        <div className="flex flex-col items-left">
          <div className="text-2xl mb-5">질문에 대한 답을 찾지 못하셨나요?</div>
          <div className="text-lg">1대1 문의하기</div>
        </div>
        <div className="flex ml-16">
          <img src={cat} className="w-50 h-50 " />
          <img src={questionPicture} className="w-50 h-70 " />
        </div>
      </section>
    </>
  );
};

export default InqueryPopular;
