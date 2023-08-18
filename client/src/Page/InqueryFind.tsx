import React from 'react';
import cat from '../Style/greencat.png';
import questionPicture from '../Style/question.png';
import InqueryComponent from '../Components/Inquery/InqueryComponent';
import QuestionSection from '../Components/Inquery/QuestionSection';
import { useNavigate } from 'react-router-dom';
import { GoToFunction } from '../Util/types';
import SearchInput from '../Components/Inquery/SearchInput';
import { useState } from 'react';
import useStore from '../store/useSearchStore';

const InqueryFind = () => {
  const navi = useNavigate();
  const goTo: GoToFunction = (path: string) => {
    navi(path);
  };
  const { inputValue } = useStore();
  const [search, setSearch] = useState('');
  console.log(inputValue);
  console.log(search);
  return (
    <>
      <div className=" flex flex-col justify-center items-center h-[45vh] bg-InqueryMain p-5">
        <div className=" text-[40px] mb-4 flex items-center p-5">
          {/* <img src={doctor} /> */}
          자주 묻는 질문
        </div>
        <SearchInput search={setSearch} />
        <QuestionSection goTo={goTo} />
      </div>
      <section className=" flex flex-col bg-#e0e0e0">
        <InqueryComponent search={inputValue} />
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

export default InqueryFind;
