import React, { useState } from 'react';
import axios from 'axios';
import ImageUploader from '../Components/Care/ImageUploader';

const CareListDetail = () => {
  const [location, setLocation] = useState('');
  const [timeRange, setTimeRange] = useState('');
  const [isPetsitter, setIsPetsitter] = useState(false);
  const [petSize, setPetSize] = useState('');
  const [extraInfo, setExtraInfo] = useState('');

  const handlePost = async () => {
    try {
      const response = await axios.post('https://server.petching.net/geshi', {
        location,
        timeRange,
        isPetsitter,
        petSize,
        extraInfo,
      });
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen text-xl">
      <div className="bg-[#F2F2F2] text-center">
        <div>찾으시는 지역</div>
        <input
          placeholder="서울시 신림동"
          value={location}
          onChange={e => setLocation(e.target.value)}
        ></input>
        <div>언제 맡기시나요?</div>
        <input
          placeholder="2023.8.2 to 2023.8.5"
          value={timeRange}
          onChange={e => setTimeRange(e.target.value)}
        ></input>
      </div>
      <div className="bg-white mt-10 text-center">
        <div>펫을 맡기시나요?</div>
        <div>
          <button
            className="bg-customGreen rounded-lg"
            onClick={() => setIsPetsitter(true)}
          >
            펫시터에요
          </button>
          <button
            className="bg-customGreen rounded-lg"
            onClick={() => setIsPetsitter(false)}
          >
            집사에요
          </button>
        </div>
        <div>펫의 크기를 지정해주세요</div>
        <div>
          <button
            className="bg-customGreen rounded-lg"
            onClick={() => setPetSize('소형')}
          >
            소형
          </button>
          <button
            className="bg-customGreen rounded-lg"
            onClick={() => setPetSize('중형')}
          >
            소형
          </button>
          <button
            className="bg-customGreen rounded-lg"
            onClick={() => setPetSize('대형')}
          >
            소형
          </button>
        </div>
        <div>
          <ImageUploader />
        </div>
        <div>추가사항을 적어주세요</div>
        <input
          className="border-slate-200"
          value={extraInfo}
          onChange={e => setExtraInfo(e.target.value)}
        ></input>
      </div>
      <div className="bg-white mt-10 text-center">
        <button className="bg-customGreen w-10 rounded-lg" onClick={handlePost}>
          게시
        </button>
      </div>
    </div>
  );
};

export default CareListDetail;
