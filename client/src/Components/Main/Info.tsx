import { useNavigate } from 'react-router-dom';

const Info = () => {
  const navigate = useNavigate();
  const toCareList = () => {
    navigate('/carelist');
  };
  return (
    <div className="w-full h-full flex justify-center items-center relative">
      <div className="absolute top-0 left-0 w-full h-full bg-[url(https://media.discordapp.net/attachments/1118151636695650308/1141566260539310101/pexels-photo-16395147.png?width=1236&height=822)] opacity-40 bg-cover bg-no-repeat bg-center"></div>
      <div className="sm:w-1/4 -mt-20 w-3/4 relative z-10 ">
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
