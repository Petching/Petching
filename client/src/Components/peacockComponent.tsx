import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { AiOutlineComment } from 'react-icons/ai';
import NoImage from '../Style/NoImage.png';
import { useGetPeacock } from '../Hook/useGetPeacock';
import { usePostLike } from '../Hook/usePostLike';

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
  const navigate = useNavigate();
  const toPeacockDetail = (boardId: number) => {
    navigate(`/peacock/${boardId}`);
  };
  return (
    <div className="flex flex-wrap mb-20">
      {GetPeacock &&
        GetPeacock.map((item: PeacockData) => (
          <div
            key={item.boardId}
            className="w-1/4 p-4 m-8 border border-gray-200"
          >
            {item.imgUrls.length > 0 ? (
              <>
                <Carousel
                  showThumbs={false}
                  showStatus={false}
                  className="flex flex-row"
                  infiniteLoop
                >
                  {/* 해당 게시물의 이미지들로 캐러셀 슬라이드 생성 */}
                  {item.imgUrls.map((imageUrl, index) => (
                    <div
                      key={index}
                      className="flex items-center justify-center h-full"
                      onClick={() => {
                        toPeacockDetail(item.boardId);
                      }}
                    >
                      <img
                        src={imageUrl}
                        alt="petimage"
                        className=" max-h-40 object-center"
                      />
                    </div>
                  ))}
                </Carousel>
                <h2 className="mt-4">{item.title}</h2>
                <div className="flex items-center">
                  <img
                    src={item.profileImgUrl}
                    alt="profile"
                    className="w-12 h-12 rounded-full"
                  />
                  <span className="ml-2">{item.nickName}</span>
                </div>
                <div className="flex items-center">
                  <AiOutlineHeart
                    onClick={() => {
                      usePostLike(item.boardId);
                    }}
                  />
                  <span className="ml-4">{item.likes}</span>
                  <AiOutlineComment className="ml-4" />
                  <span className="ml-4">{item.commentCount}</span>
                </div>
              </>
            ) : (
              <div className="p-4 m-2">
                {/* Replace with actual image URL or default image URL */}
                <img
                  src={NoImage}
                  alt="Default Image"
                  className="max-h-40 object-center"
                />
              </div>
            )}
          </div>
        ))}
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
