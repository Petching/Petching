import React from 'react';
import { GoToFunction } from '../../Util/types';

interface QuestionSectionProps {
  goTo?: GoToFunction;
}

const QuestionSection: React.FC<QuestionSectionProps> = ({ goTo }) => {
  return (
    <section className=" bg-white shadow-custom mx-auto flex flex-col w-auto h-auto sm:w-[500px] sm:h-[180px] md:w-[600px] md:h-[200px]  border border-#e0e0e0 rounded-3xl overflow-hidden text-2xl">
      <div className="flex h-full ">
        <button
          onClick={() => goTo && goTo('/inquery')}
          className="flex-1 flex justify-center items-center hover:bg-InqueryComponentButton"
        >
          질문 홈
        </button>
        {/* goTo &&를 써서 null, false 등이 값이 아닌걸 확인 */}
        <button
          onClick={() => goTo && goTo('/inquery/popular')}
          className="flex-1 flex justify-center items-center hover:bg-InqueryComponentButton"
        >
          인기질문
        </button>
        <button
          onClick={() => goTo && goTo('/inquery/map')}
          className="flex-1 flex justify-center items-center hover:bg-InqueryComponentButton"
        >
          찾아오시는 길
        </button>
        <button
          onClick={() => goTo && goTo('/inquery/map')}
          className="flex-1 flex justify-center items-center hover:bg-InqueryComponentButton"
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
