/* eslint-disable */
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { AiOutlineComment } from 'react-icons/ai';
import NoImage from '../../Style/NoImage.png';
import { useGetPeacock } from '../../Hook/useGetPeacock';
import { usePostLike } from '../../Hook/usePostLike';
import { usePeacockBoardIdStore } from '../../store/PeacockDetailStore';

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
  const setpeacockBoardId = usePeacockBoardIdStore(state => state.setBoardId);
  const toPeacockDetail = (boardId: number) => {
    navigate(`/peacock/${boardId}`);
    setpeacockBoardId(boardId);
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
                      className="flex items-center justify-center h-full cursor-pointer"
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
