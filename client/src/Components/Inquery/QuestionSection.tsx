import React from 'react';
import { GoToFunction } from '../../Util/types';

interface QuestionSectionProps {
  goTo?: GoToFunction;
}

const QuestionSection: React.FC<QuestionSectionProps> = ({ goTo }) => {
  return (
    <section className=" bg-white shadow-custom mx-auto flex flex-col mt-5 space-y-3 w-full h-full sm:w-full sm:h-full md:w-[600px] md:h-[400px]  border border-#e0e0e0 rounded-3xl overflow-hidden text-2xl">
      <div className="flex h-1/2">
        <button
          onClick={() => goTo && goTo('/inquery')}
          className="flex-1 flex justify-center items-center hover:bg-green-100"
        >
          질문 홈
        </button>
        {/* goTo &&를 써서 null, false 등이 값이 아닌걸 확인 */}
        <button
          onClick={() => goTo && goTo('/inquery/popular')}
          className="flex-1 flex justify-center items-center hover:bg-green-100"
        >
          인기질문
        </button>
        <button
          onClick={() => goTo && goTo('/inquery/first')}
          className="flex-1 flex justify-center items-center hover:bg-green-100"
        >
          이용 가이드
        </button>
      </div>
      <div className="flex  h-1/2">
        <button className="flex-1 flex justify-center items-center hover:bg-green-100">
          준비중
        </button>
        <button className="flex-1 flex justify-center items-center hover:bg-green-100">
          준비중
        </button>
        <button className="flex-1 flex justify-center items-center hover:bg-green-100">
          준비중
        </button>
      </div>
    </section>
  );
};

export default QuestionSection;
