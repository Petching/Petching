import Board from '../Components/Care/Board';
import Card from '../Components/Care/Card';
import TextEditor from '../Components/Care/TextEditor';

const CareList = () => {
  return (
    <div className="text-[1.2rem]">
      <div className="flex flex-col items-center bg-gray-100 mx-20">
        <div className="flex">
          <div className="mr-4">
            <div className="leading-10 ">어느 지역을 찾으시나요?</div>
            <div className="relative">
              <input
                placeholder="동 이름을 검색하세요"
                className="w-60 h-10"
              ></input>
            </div>
          </div>
          <div>
            <div className="leading-10">언제 맡기시나요?</div>
            <input type="date" className="w-60 h-10" />
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
      </div>
      <div>
        <TextEditor></TextEditor>
      </div>
      <Board></Board>
    </div>
  );
};

export default CareList;
