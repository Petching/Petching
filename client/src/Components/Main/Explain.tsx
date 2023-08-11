import { useNavigate } from 'react-router-dom';

const Explain = () => {
  const navigate = useNavigate();
  const toCareList = () => {
    navigate('/carelist');
  };
  return (
    <div className="w-full h-full flex justify-evenly items-center flex-col sm:flex-row">
      <p className="-mt-20 text-xl">
        원하는 조건으로
        <br />
        당신에게 맞는
        <br />
        펫시터를 찾을 수 있습니다.
      </p>
      <div className="-mt-20 flex justify-between items-center flex-col sm:flex-row">
        <button
          className="w-60 h-24 mt-2 sm:mt-0 sm:h-96 relative rounded overflow-hidden flex justify-center items-center"
          onClick={toCareList}
        >
          <div className="w-full h-full absolute left-0 top-0 bg-[url(https://cdn.pixabay.com/photo/2016/12/13/05/15/puppy-1903313_1280.jpg)] opacity-50 hover:opacity-80 hover:scale-105 transition-all bg-cover bg-center"></div>
          <p className="text-2xl ">지역</p>
        </button>
        <button
          className="w-60 h-24 mt-2 sm:mt-0 sm:h-96 relative rounded overflow-hidden flex justify-center items-center mx-5"
          onClick={toCareList}
        >
          <div className="w-full h-full absolute left-0 top-0 bg-[url(https://cdn.pixabay.com/photo/2017/02/15/12/12/cat-2068462_1280.jpg)] opacity-50 hover:opacity-80 hover:scale-105 transition-all bg-cover bg-center"></div>
          <p className="text-2xl ">기간</p>
        </button>
        <button
          className="w-60 h-24 mt-2 sm:mt-0 sm:h-96 relative rounded overflow-hidden flex justify-center items-center"
          onClick={toCareList}
        >
          <div className="w-full h-full absolute left-0 top-0 bg-[url(https://cdn.pixabay.com/photo/2017/04/03/17/35/animals-2198994_1280.jpg)] opacity-50 hover:opacity-80 hover:scale-105 transition-all bg-cover bg-center"></div>
          <p className="text-2xl ">그 외 조건</p>
        </button>
      </div>
    </div>
  );
};

export default Explain;
