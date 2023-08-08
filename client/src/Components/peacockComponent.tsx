import React, { useState } from 'react';
import pit from '../Style/kakaoLogo.png';
import { useNavigate } from 'react-router-dom';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { AiOutlineComment } from 'react-icons/ai';
// import doctor from '../Style/doctor.jpg';
// import doctors from '../Style/doctors.jpg';
// import pills from '../Style/pills.jpg';
import { useGetPeacock } from '../Hook/useGetPeacock';

export const CarouselComponent: React.FC = () => {
  const { GetPeacock } = useGetPeacock();

  interface PictureItem {
    id: number;
    imgurl: string;
  }

  function changearray(el: any[]): PictureItem[] {
    const picture: PictureItem[] = [];
    for (let i = 0; i < el.length; i++) {
      const id = i + 1;
      const imgurl = el[i];
      picture.push({ id, imgurl });
    }
    return picture;
  }
  let pictureItems: PictureItem[] = [];

  if (GetPeacock) {
    pictureItems = changearray(GetPeacock[0].imgUrls);
  }

  return (
    <Carousel
      showStatus={false}
      showIndicators={false}
      showThumbs={false}
      infiniteLoop={true}
    >
      {pictureItems.map(el => (
        <div key={el.id}>
          <img
            src={el.imgurl}
            alt="cau"
            style={{ maxHeight: '100%', maxWidth: '100%' }}
          />
        </div>
      ))}
    </Carousel>
  );
};

export const LikeComponent = () => {
  const { GetPeacock } = useGetPeacock();

  const [count, setCount] = useState(1);
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
    <>
      {GetPeacock &&
        GetPeacock.map(el => (
          <div key={el.boardId} className="flex items-center">
            <button className="" onClick={handleLike}>
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
            <span className="ml-4">댓글 {el.commentCount}개</span>
          </div>
        ))}
    </>
  );
};

export const Square = () => {
  const navigate = useNavigate();
  const toPeacockWrite = () => {
    navigate('/peacock/write');
  };
  return (
    <div className="mt-12 w-4/5 h-40 bg-[#f2f2f2] flex items-center justify-around">
      <div className="w-4/6 h-28 bg-[#d9d9d9] text-4xl flex items-center justify-center">
        귀여운 반려동물을 자랑해 보세요!
      </div>
      <button
        className="w-1/4 h-28 text-4xl bg-[#bcbcbc] rounded-full flex items-center justify-center"
        onClick={toPeacockWrite}
      >
        글 작성하기
      </button>
    </div>
  );
};

export const PeacockComponent = () => {
  const { GetPeacock } = useGetPeacock();

  return (
    <div className="h-96 w-72 m-12 bg-[#f2f2f2]">
      <CarouselComponent />
      <div className="mt-3 text-center">노가르시아</div>
      {GetPeacock &&
        GetPeacock.map(el => (
          <div key={el.boardId} className="flex items-center ml-2">
            <img className="w-16 h-16" src={pit} alt="logo" />
            <span className="ml-4">{el.nickName}</span>
          </div>
        ))}
      <div className="flex items-center mt-8 ml-2">
        <LikeComponent />
      </div>
    </div>
  );
};
