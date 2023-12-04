import React, { useState } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import ImageUploader from '../Components/Care/ImageUploader';
import ReactCalendar from '../Components/Care/ReactCalendar';
import Postcode from '../Components/Care/Postcode';
import { postImgHandler } from '../Util/postImg';
import { useEffect } from 'react';
import useDateSelect from '../Hook/useDateSelect';
import { useNavigate } from 'react-router-dom';
import { useStore } from '../store/editPost';

const CareListPost = () => {
  const location = useLocation();
  const { postToEdit, isEdit, setIsEdit } = useStore();
  const [title, setTitle] = useState(isEdit ? postToEdit.title : '');
  const { startDate, endDate, onDateSelected } = useDateSelect();
  const [locationTag, setLocationTag] = useState(
    isEdit ? postToEdit?.locationTag : location.state?.locationTag || '',
  );
  const [isPetsitter, setIsPetsitter] = useState(
    isEdit ? postToEdit.isPetsitter : true,
  );
  const [content, setContent] = useState(isEdit ? postToEdit.content : '');
  const [petSizes, setpetSizes] = useState<string[]>(
    isEdit ? postToEdit.petSizes : [],
  );
  const [memo, setMemo] = useState(isEdit ? postToEdit.memo : '');
  const [imagesToUpload, setImagesToUpload] = useState<File[]>(
    isEdit ? postToEdit.imgUrls : [],
  );
  const navigate = useNavigate();
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
      if (isEdit) {
        // 수정 요청
        await axios.patch(
          `https://server.petching.net/careposts/${postToEdit.postId}`,
          requestBody,
          {
            headers: { Authorization: token },
          },
        );
        console.log(requestBody);
        setIsEdit(false);
      } else {
        // 새 게시물 작성 요청
        await axios.post('https://server.petching.net/careposts', requestBody, {
          headers: { Authorization: token },
        });
      }
      navigate('/carelist');
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
    if (isPetsitter) {
      setTitle('');
      setContent('');
    } else {
      if (postToEdit) {
        setTitle(postToEdit.title);
        setContent(postToEdit.content);
      }
    }
  }, [isPetsitter, postToEdit]);

  return (
    <div className="bg-[#F2F2F2] w-full h-[80rem] min-h-screen text-xl ">
      <div className="bg-[#F2F2F2] h-10 mt-[4rem]"></div>
      <div className="bg-[#ffffff] mx-auto w-[40rem] h-[15rem] rounded-lg  text-center">
        <div className="h-4"></div>
        <div>찾으시는 지역</div>
        <Postcode onAddressSelected={setLocationTag} value={locationTag} />
        <div className="mt-4">언제 맡기시나요?</div>
        <ReactCalendar onDateSelected={onDateSelected} />
      </div>
      <div className="bg-white mx-auto w-[40rem] h-[42rem] rounded-lg mt-10 text-center ">
        <div className="h-1 "></div>
        <div className="mt-10 mb-3 ml-[-27rem] ">펫을 맡기시나요?</div>
        <div className="ml-[25rem]">
          <button
            className={`w-24 h-10 rounded-lg bg-customGreen ${
              isPetsitter ? 'text-white' : 'text-black'
            }`}
            onClick={() => setIsPetsitter(true)}
          >
            펫시터에요
          </button>
          <button
            className={`w-24 h-10 rounded-lg mx-4 bg-customGreen ${
              isPetsitter ? 'text-black' : 'text-white'
            }`}
            onClick={() => setIsPetsitter(false)}
          >
            집사에요
          </button>
        </div>
        {isPetsitter ? (
          <>
            <div className="h-1 "></div>
            <div className="mt-2 ml-[-26rem]">글제목을 입력하세요</div>
            <input
              className="border-4 ml-[22rem] w-64 h-10"
              value={title}
              onChange={e => setTitle(e.target.value)}
            />
            <div className="mt-2 ml-[-26.5rem]">내용을 입력하세요</div>
            <textarea
              className="border-4 ml-[22rem] w-64 h-20"
              value={content}
              onChange={e => setContent(e.target.value)}
            />
          </>
        ) : (
          <>
            <div className="h-1 "></div>
            <div className="mt-2 ml-[-26rem]">글제목을 입력하세요</div>
            <input
              className="border-4 ml-[22rem] w-64 h-10"
              value={title}
              onChange={e => setTitle(e.target.value)}
            />
            <div className="mt-2 ml-[-26.5rem]">내용을 입력하세요</div>
            <textarea
              className="border-4 ml-[22rem] w-64 h-20"
              value={content}
              onChange={e => setContent(e.target.value)}
            />
          </>
        )}
        <div className="mt-5 ml-[7rem]">
          <ImageUploader onUploadComplete={handleImageUploaded} />
        </div>
        <div className="mt-6 ml-[-23rem]">펫의 크기를 지정해주세요</div>
        <div className="ml-[22.5rem]">
          <button
            className={`bg-customGreen rounded-lg mx-2 w-16 h-10 ${
              petSizes.includes('소형') ? 'text-white' : ''
            }`}
            onClick={() => handlepetSizesClick('소형')}
          >
            소형
          </button>
          <button
            className={`bg-customGreen rounded-lg mx-2 w-16 h-10 ${
              petSizes.includes('중형') ? 'text-white' : ''
            }`}
            onClick={() => handlepetSizesClick('중형')}
          >
            중형
          </button>
          <button
            className={`bg-customGreen rounded-lg mx-2 w-16 h-10 ${
              petSizes.includes('대형') ? 'text-white' : ''
            }`}
            onClick={() => handlepetSizesClick('대형')}
          >
            대형
          </button>
        </div>

        <div className="mt-3 ml-[-24.5rem]">추가사항을 적어주세요</div>
        <textarea
          className="border-4 ml-[22rem] w-64 h-20"
          value={memo}
          onChange={e => setMemo(e.target.value)}
        ></textarea>
      </div>
      <div className="text-center">
        <button
          className="bg-customPink rounded-md mt-10 w-[40rem] h-20"
          onClick={handlePost}
        >
          {isEdit ? '수정하기' : '게시'}
        </button>
      </div>
    </div>
  );
};

export default CareListPost;
