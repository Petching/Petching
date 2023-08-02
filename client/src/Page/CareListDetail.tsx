import React from 'react';
import ImageUploader from '../Components/Care/ImageUploader';
const CareListDetail = () => {
  return (
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen text-xl">
      <div className="bg-[#F2F2F2] text-center">
        <div>찾으시는 지역</div>
        <input placeholder="서울시 신림동"></input>
        <div>언제 맡기시나요?</div>
        <input placeholder="2023.8.2 to 2023.8.5"></input>
      </div>
      <div className="bg-white mt-10 text-center">
        <div>펫을 맡기시나요?</div>
        <div>
          <button className="bg-customGreen rounded-lg">펫시터에요</button>
          <button className="bg-customGreen rounded-lg">집사에요</button>
        </div>
        <div>펫의 크기를 지정해주세요</div>
        <div>
          <button className="bg-customGreen rounded-lg">소형</button>
          <button className="bg-customGreen rounded-lg">중형</button>
          <button className="bg-customGreen rounded-lg">대형</button>
        </div>
        <div>
          <ImageUploader />
        </div>

        <div>추가사항을 적어주세요</div>
        <input className="border-slate-200"></input>
      </div>
      <div className="bg-white mt-10 text-center">
        <button className="bg-customGreen w-10 rounded-lg">게시</button>
      </div>
    </div>
  );
};

export default CareListDetail;
