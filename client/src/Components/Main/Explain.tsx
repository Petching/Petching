import { useNavigate } from 'react-router-dom';

const Explain = () => {
  const navigate = useNavigate();
  const toCareList = () => {
    navigate('/carelist');
  };
  return (
    <div className="w-full h-full flex justify-evenly items-center">
      <p className="-mt-20 text-xl">
        원하는 조건으로
        <br />
        당신에게 맞는
        <br />
        펫시터를 찾을 수 있습니다.
      </p>
      <div className="-mt-20 flex justify-between items-center">
        <button
          className="w-60 h-96 relative rounded overflow-hidden flex justify-center items-center"
          onClick={toCareList}
        >
          <div className="w-full h-full absolute left-0 top-0 bg-[url(https://placekitten.com/1400)] opacity-50 hover:opacity-80 hover:scale-105 transition-all"></div>
          <p className="text-2xl ">지역</p>
        </button>
        <button
          className="w-60 h-96 relative rounded overflow-hidden flex justify-center items-center mx-5"
          onClick={toCareList}
        >
          <div className="w-full h-full absolute left-0 top-0 bg-[url(https://placekitten.com/1400)] opacity-50 hover:opacity-80 hover:scale-105 transition-all"></div>
          <p className="text-2xl ">기간</p>
        </button>
        <button
          className="w-60 h-96 relative rounded overflow-hidden flex justify-center items-center"
          onClick={toCareList}
        >
          <div className="w-full h-full absolute left-0 top-0 bg-[url(https://placekitten.com/1400)] opacity-50 hover:opacity-80 hover:scale-105 transition-all"></div>
          <p className="text-2xl ">그 외 조건</p>
        </button>
      </div>
    </div>
  );
};

export default Explain;
