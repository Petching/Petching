import React from 'react';

const photoboxComponent = () => {
  return (
    <div className="h-40 w-5/6 flex items-center justify-around bg-[#f2f2f2]">
      <div className="h-28 w-3/5 flex text-5xl justify-center items-center text-center  bg-[#d9d9d9]">
        귀여운 반려동물을 자랑해보세요!
      </div>
      <div className="h-28 w-1/5 flex  text-5xl justify-center items-center rounded-full  bg-[#bcbcbc]">
        글 작성하기
      </div>
    </div>
  );
};

export default photoboxComponent;
