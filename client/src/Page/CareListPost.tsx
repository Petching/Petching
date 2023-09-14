import React, { useState } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import ImageUploader from '../Components/Care/ImageUploader';
import ReactCalendar from '../Components/Care/ReactCalendar';
import Postcode from '../Components/Care/Postcode';
import { postImgHandler } from '../Util/postImg';
import { useEffect } from 'react';
import useDateSelect from '../Hook/useDateSelect';
const CareListPost = () => {
  const location = useLocation();
  const { startDate, endDate, onDateSelected } = useDateSelect();
  const [locationTag, setLocationTag] = useState(location.state.locationTag);
  const [isPetsitter, setIsPetsitter] = useState(true);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [petSizes, setpetSizes] = useState<string[]>([]);
  const [memo, setMemo] = useState('');
  const [imagesToUpload, setImagesToUpload] = useState<File[]>([]);

  const handleImageUploaded = (uploadedUrls: File[]) => {
    setImagesToUpload(uploadedUrls);
  };

  const handlePost = async () => {
    const token = localStorage.getItem('ACCESS_TOKEN');
    const imgUrls: string[] = [];
    for (const img of imagesToUpload) {
      const imgUrl = await postImgHandler(img, 'careposts');
      imgUrls.push(imgUrl);
    }
    try {
      const requestBody = {
        title,
        content,
        imgUrls,
        startDate,
        endDate,
        conditionTag: isPetsitter ? '펫시터예요' : '집사예요',
        locationTag,
        petSizes,
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

  const handlepetSizesClick = (size: string) => {
    if (petSizes.includes(size)) {
      setpetSizes(petSizes.filter(s => s !== size));
    } else {
      setpetSizes([...petSizes, size]);
    }
  };

  useEffect(() => {
    setTitle(isPetsitter ? '' : '펫시터 찾아요 👀');
    setContent(isPetsitter ? '' : '펫시터분 문의주세요~');
  }, [isPetsitter]);

  return (
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen text-xl">
      <div className="bg-[#F2F2F2] h-10 mt-[4rem]"></div>
      <div className="bg-[#F2F2F2] text-center">
        <div>찾으시는 지역</div>
        <Postcode onAddressSelected={setLocationTag} value={locationTag} />
        <div>언제 맡기시나요?</div>
        <ReactCalendar onDateSelected={onDateSelected} />
      </div>
      <div className="bg-white mx-auto w-1/2 h-[30rem] rounded-lg mt-10 text-center">
        <div className="h-1 "></div>
        <div className="mt-10 mx-16">펫을 맡기시나요?</div>
        <div>
          <button
            className="bg-customGreen rounded-lg"
            onClick={() => setIsPetsitter(true)}
          >
            펫시터에요
          </button>
          <button
            className="bg-customGreen rounded-lg mx-4"
            onClick={() => setIsPetsitter(false)}
          >
            집사에요
          </button>
        </div>
        {isPetsitter ? (
          <>
            <div className="h-1 "></div>
            <div className="mt-2">글제목을 입력하세요</div>
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
        ) : (
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
        <div className="mt-3">펫의 크기를 지정해주세요</div>
        <div>
          <button
            className={`bg-customGreen rounded-lg mx-5 w-16 ${
              petSizes.includes('소형') ? 'text-white' : ''
            }`}
            onClick={() => handlepetSizesClick('소형')}
          >
            소형
          </button>
          <button
            className={`bg-customGreen rounded-lg mx-5 w-16 ${
              petSizes.includes('중형') ? 'text-white' : ''
            }`}
            onClick={() => handlepetSizesClick('중형')}
          >
            중형
          </button>
          <button
            className={`bg-customGreen rounded-lg mx-5 w-16 ${
              petSizes.includes('대형') ? 'text-white' : ''
            }`}
            onClick={() => handlepetSizesClick('대형')}
          >
            대형
          </button>
        </div>
        <div className="mt-3">
          <ImageUploader onUploadComplete={handleImageUploaded} />
        </div>
        <div className="mt-3">추가사항을 적어주세요</div>
        <input
          className="border-2"
          value={memo}
          onChange={e => setMemo(e.target.value)}
        ></input>
      </div>
      <div className="text-center">
        <button
          className="bg-customPink rounded-md mt-10 w-1/2 h-20"
          onClick={handlePost}
        >
          게시
        </button>
      </div>
    </div>
  );
};

export default CareListPost;
