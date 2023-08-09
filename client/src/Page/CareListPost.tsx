import React, { useState } from 'react';
// import { Axios } from '../../src/API/api';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import ImageUploader from '../Components/Care/ImageUploader';
import ReactCalendar from '../Components/Care/ReactCalendar';
import Postcode from '../Components/Care/Postcode';

const CareListPost = () => {
  const location = useLocation();
  const [startDate, setStartDate] = useState(location.state.startDate);
  const [endDate, setEndDate] = useState(location.state.endDate);
  const [locationTag, setLocationTag] = useState(location.state.locationTag);
  const [isPetsitter, setIsPetsitter] = useState(false);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [petSize, setPetSize] = useState('');
  const [memo, setMemo] = useState('');
  const [imgUrls, setImgUrls] = useState<string[]>([]);

  const handleImageUploaded = (uploadedUrls: string[]) => {
    setImgUrls([...imgUrls, ...uploadedUrls]);
  };

  const handlePost = async () => {
    const token = localStorage.getItem('ACCESS_TOKEN');
    try {
      const requestBody = {
        title,
        content,
        imgUrls,
        startDate,
        endDate,
        conditionTag: isPetsitter ? '펫시터예요' : '집사예요',
        locationTag,
        petSize,
        memo,
      };
      const response = await axios.post(
        'https://server.petching.net/careposts',
        requestBody,
        {
          headers: { Authorization: token },
        },
      );
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };
  const onDateSelected = (value: any) => {
    if (value.from && value.to) {
      setStartDate({
        year: value.from.year,
        month: value.from.month,
        day: value.from.day,
      });
      setEndDate({
        year: value.to.year,
        month: value.to.month,
        day: value.to.day,
      });
    }
  };

  return (
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen text-xl">
      <div className="bg-[#F2F2F2] text-center">
        <div>찾으시는 지역</div>
        <Postcode onAddressSelected={setLocationTag} value={locationTag} />
        <div>언제 맡기시나요?</div>
        <ReactCalendar
          onDateSelected={onDateSelected}
          value={{ from: startDate, to: endDate }}
        />
      </div>
      <div className="bg-white mt-10 text-center">
        <div>펫을 맡기시나요?</div>
        <div>
          <button
            className="bg-customGreen rounded-full"
            onClick={() => setIsPetsitter(true)}
          >
            펫시터에요
          </button>
          <button
            className="bg-customGreen rounded-full mx-4"
            onClick={() => setIsPetsitter(false)}
          >
            집사에요
          </button>
        </div>
        {isPetsitter && (
          <>
            <div>글제목을 입력하세요</div>
            <input
              className="border-2"
              value={title}
              onChange={e => setTitle(e.target.value)}
            />
            <div>내용을 입력하세요</div>
            <input
              className="border-2"
              value={content}
              onChange={e => setContent(e.target.value)}
            />
          </>
        )}
        <div>펫의 크기를 지정해주세요</div>
        <div>
          <button
            className="bg-customGreen rounded-full mx-4"
            onClick={() => setPetSize('소형')}
          >
            소형
          </button>
          <button
            className="bg-customGreen rounded-full mx-4"
            onClick={() => setPetSize('중형')}
          >
            중형
          </button>
          <button
            className="bg-customGreen rounded-full mx-4"
            onClick={() => setPetSize('대형')}
          >
            대형
          </button>
        </div>
        <div>
          <ImageUploader onUploadComplete={handleImageUploaded} />
        </div>
        <div>추가사항을 적어주세요</div>
        <input
          className="border-2"
          value={memo}
          onChange={e => setMemo(e.target.value)}
        ></input>
      </div>
      <div className="bg-white mt-10 text-center">
        <button
          className="bg-customPink w-10 rounded-full"
          onClick={handlePost}
        >
          게시
        </button>
      </div>
    </div>
  );
};

export default CareListPost;
