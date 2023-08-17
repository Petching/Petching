import { useNavigate } from 'react-router-dom';

const Info = () => {
  const navigate = useNavigate();
  const toCareList = () => {
    navigate('/carelist');
  };
  return (
    <div className="w-full h-full flex justify-center items-center">
      <div className="sm:w-1/4 -mt-20 w-3/4">
        <p className="text-center text-2xl">
          소중한 우리 댕냥이
          <br />
          집에 혼자 두기엔
          <br />
          너무 불안하시죠!
          <br />
          <br />
          펫칭에서 돌보미를
          <br />
          찾아보세요!
        </p>
        <button
          className="w-full h-16 mt-10 bg-customPink text-xl hover:scale-90 transition-all rounded"
          onClick={toCareList}
        >
          펫시터 찾아보기
        </button>
      </div>
    </div>
  );
};

export default Info;
