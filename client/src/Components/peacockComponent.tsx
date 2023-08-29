import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { AiOutlineComment } from 'react-icons/ai';
// import doctor from '../Style/doctor.jpg';
// import doctors from '../Style/doctors.jpg';
// import pills from '../Style/pills.jpg';
import { useGetPeacock } from '../Hook/useGetPeacock';

interface PeacockData {
  boardId: number;
  title: string;
  profileImgUrl: string;
  nickName: string;
  likes: number;
  createdAt: string;
  commentCount: number;
  checkLike: boolean;
  imgUrls: string[];
}

export const CarouselComponent: React.FC = () => {
  const { GetPeacock } = useGetPeacock();

  interface PeacockData {
    boardId: number;
    title: string;
    profileImgUrl: string;
    nickName: string;
    likes: number;
    createdAt: string;
    commentCount: number;
    checkLike: boolean;
    imgUrls: string[];
  }
  return (
    <Carousel showThumbs={false}>
      {GetPeacock &&
        GetPeacock.map((item: PeacockData) => (
          <div key={item.boardId}>
            <img src={item.imgUrls[0]} alt={item.title} />
            <p>{item.title}</p>
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

const PeacockCarousel: React.FC<PeacockComponentProps> = ({ data }) => {
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
    <div>
      <img src={data.imgUrls[0]} alt={data.title} className="w-40 h-40" />
      <p>{data.title}</p>
      <div className="flex items-center">
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
        <span className="ml-4">댓글 {data.commentCount}개</span>
      </div>
    </div>
  );
};

export const PeacockComponent: React.FC = () => {
  const { GetPeacock } = useGetPeacock();

  return (
    <Carousel showThumbs={false}>
      {GetPeacock &&
        GetPeacock.map((item: PeacockData) => (
          <PeacockCarousel key={item.boardId} data={item} />
        ))}
    </Carousel>
  );
};

interface PeacockComponentProps {
  data: PeacockData;
}
