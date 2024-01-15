import React from 'react';
import { GoToFunction } from '../../Util/types';

interface QuestionSectionProps {
  goTo?: GoToFunction;
}

const QuestionSection: React.FC<QuestionSectionProps> = ({ goTo }) => {
  return (
    <section className="z-10 bg-white shadow-custom mx-auto flex flex-col sm:w-[500px] sm:h-[180px] md:w-[600px] md:h-[100px] lg:h-[200px]  border border-#e0e0e0 rounded-3xl overflow-hidden text-2xl">
      <div className="flex h-full ">
        <button
          onClick={() => goTo && goTo('/inquery')}
          className="transform transition-transform duration-300 hover:scale-105  flex-1 flex justify-center items-center hover:bg-InqueryComponentButton text-[20px] sm:text-[23px] md:text-[28px]"
        >
          질문 홈
        </button>
        {/* goTo &&를 써서 null, false 등이 값이 아닌걸 확인 */}
        <button
          onClick={() => goTo && goTo('/inquery/popular')}
          className="transform transition-transform duration-300 hover:scale-105  flex-1 flex justify-center items-center hover:bg-InqueryComponentButton text-[20px] sm:text-[23px] md:text-[28px]"
        >
          인기질문
        </button>
        <button
          onClick={() => goTo && goTo('/inquery/map')}
          className="transform transition-transform duration-300 hover:scale-105  flex-1 flex justify-center items-center hover:bg-InqueryComponentButton text-[20px] sm:text-[23px] md:text-[28px]"
        >
          오시는 길
        </button>
        <button
          onClick={() => goTo && goTo('/inquery/write')}
          className="transform transition-transform duration-300 hover:scale-105  flex-1 flex justify-center items-center hover:bg-InqueryComponentButton text-[20px] sm:text-[23px] md:text-[28px]"
        >
          문의하기
        </button>
      </div>
      {/* <div className="flex  h-1/2">
        <button className="flex-1 flex justify-center items-center hover:bg-InqueryComponentButton">
          준비중
        </button>
        <button className="flex-1 flex justify-center items-center hover:bg-InqueryComponentButton">
          준비중
        </button>
        <button className="flex-1 flex justify-center items-center hover:bg-InqueryComponentButton">
          준비중
        </button>
      </div> */}
    </section>
  );
};

export default QuestionSection;
