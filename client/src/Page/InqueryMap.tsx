import poo from '../Style/footerdog.png';
import QuestionSection from '../Components/Inquery/QuestionSection';
import { useNavigate } from 'react-router-dom';
import { GoToFunction } from '../Util/types';
import SearchInput from '../Components/Inquery/SearchInput';
import { useState } from 'react';
import useStore from '../store/useSearchStore';
import Map from '../Components/Inquery/Map';
import inquerydog from '../Style/inquerydog.png';
import mainimg from '../Style/silver.png';

const InqueryMap = () => {
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
      <section className=" flex flex-col bg-#e0e0e0 justify-center items-center mt-10 mb-10">
        <Map />
      </section>
      <section className="flex justify-center h-[20vh] bg-gray-100 p-10 overflow-hidden">
        <div className="flex flex-col items-left">
          <div className="text-3xl mb-5">질문에 대한 답을 찾지 못하셨나요?</div>
          <div className="text-lg">1대1 문의하기</div>
        </div>
        <div className="flex ml-16">
          <img src={poo} className="w-[160px] h-[120px]" />
        </div>
      </section>
    </>
  );
};

export default InqueryMap;
