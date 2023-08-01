import React from 'react';

const CareListDetail = () => {
  return (
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen mb-5">
      <div className="bg-white mt-5">
        <div className="bg-white mt-5">찾으시는 지역</div>
        <input placeholder="서울시 신림동"></input>
        <div>언제 맡기시나요?</div>
        <input placeholder="2023.8.2 to 2023.8.5"></input>
      </div>
      <div className="bg-white mt-5">
        <div>펫을 맡기시나요?</div>
      </div>
    </div>
  );
};

export default CareListDetail;
