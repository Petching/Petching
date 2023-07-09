import React, { useState, useEffect, useRef } from 'react';
import pit from '../Style/kakaoLogo.png';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { AiOutlineComment } from 'react-icons/ai';
import doctor from '../Style/doctor.jpg';
import doctors from '../Style/doctors.jpg';
import pills from '../Style/pills.jpg';

const CarouselComponent: React.FC = () => {
  const slides = [
    {
      id: 1,
      image: doctor,
      legend: 'Doctor',
    },
    {
      id: 2,
      image: doctors,
      legend: 'Doctors',
    },
    {
      id: 3,
      image: pills,
      legend: 'Pills',
    },
  ];

  return (
    <Carousel
      showStatus={false}
      showIndicators={false}
      showThumbs={false}
      infiniteLoop={true}
    >
      {slides.map(slide => (
        <div key={slide.id}>
          <img src={slide.image} alt={slide.legend} />
        </div>
      ))}
    </Carousel>
  );
};

const LikeComponent = () => {
  const [count, setCount] = useState(0);
  const [isLiked, setIsLiked] = useState(false);

  const handleLike = () => {
    if (!isLiked) {
      setCount(count + 1);
      setIsLiked(true);
    } else {
      setCount(count - 1);
      setIsLiked(false);
    }
  };

  return (
    <div className="flex items-center">
      <button className="ml-4" onClick={handleLike}>
        {isLiked ? (
          <AiFillHeart className="w-6 h-6" color="red" />
        ) : (
          <AiOutlineHeart className="w-6 h-6" />
        )}
      </button>
      <span className="ml-4">좋아요 {count}개</span>
      <button className="flex items-center ml-10">
        <AiOutlineComment className="w-6 h-6" />
      </button>
      <span className="ml-4">댓글 2개</span>
    </div>
  );
};

export const Square = () => {
  return (
    <div className="mt-12 w-4/5 h-40 bg-[#f2f2f2] flex items-center justify-around">
      <div className="w-4/6 h-28 bg-[#d9d9d9] text-4xl flex items-center justify-center">
        귀여운 반려동물을 자랑해 보세요!
      </div>
      <div className="w-1/4 h-28 text-4xl bg-[#bcbcbc] rounded-full flex items-center justify-center">
        글 작성하기
      </div>
    </div>
  );
};

export const PeacockComponent = () => {
  return (
    <div className="h-96 w-72 m-12 bg-[#f2f2f2]">
      <CarouselComponent />
      <div className="mt-3 text-center">노가르시아</div>
      <div className="flex items-center ml-2">
        <img className="w-16 h-16" src={pit} alt="logo" />
        <span className="ml-4">sdasd</span>
      </div>
      <div className="flex items-center mt-8 ml-2">
        <LikeComponent />
      </div>
    </div>
  );
};
