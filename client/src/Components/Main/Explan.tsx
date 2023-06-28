const Explan = () => {
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
        <div className="w-60 h-96 relative rounded overflow-hidden flex justify-center items-center">
          <div className="w-full h-full absolute left-0 top-0 bg-[url(https://placekitten.com/1400)] opacity-50"></div>
          <p className="text-2xl ">지역</p>
        </div>
        <div className="w-60 h-96 relative rounded overflow-hidden flex justify-center items-center mx-5">
          <div className="w-full h-full absolute left-0 top-0 bg-[url(https://placekitten.com/1400)] opacity-50"></div>
          <p className="text-2xl ">기간</p>
        </div>
        <div className="w-60 h-96 relative rounded overflow-hidden flex justify-center items-center">
          <div className="w-full h-full absolute left-0 top-0 bg-[url(https://placekitten.com/1400)] opacity-50"></div>
          <p className="text-2xl ">그 외 조건</p>
        </div>
      </div>
    </div>
  );
};

export default Explan;
