/* eslint-disable */
import React, { useState, useEffect } from 'react';
import bulldog from '../../Style/bulldog.jpg';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { AiOutlineComment } from 'react-icons/ai';
import { useGetPeacockDetail } from '../../Hook/useGetPeacockDetail';
import { usePeacockBoardIdStore } from '../../store/PeacockDetailStore';
import { Carousel } from 'react-responsive-carousel';
import { usePostLike } from '../../Hook/usePostLike';

const PeacockDetailComponent = () => {
  const peacockBoardId = usePeacockBoardIdStore(state => state.boardId);
  const setPeacockBoardId = usePeacockBoardIdStore(state => state.setBoardId);
  const { GetPeacockDetail } = useGetPeacockDetail(peacockBoardId);

  return (
    <div className="flex items-center justify-center h-full mb-12">
      <div className="mt-20 w-5/6 bg-[#f2f2f2] flex flex-col items-center ">
        <div className="w-96 h-auto overflow-hidden">
          <Carousel
            showThumbs={false}
            showStatus={false}
            className="fflex flex-row mt-12 w-full"
            infiniteLoop
          >
            {GetPeacockDetail &&
              GetPeacockDetail.imgUrls.map((imageUrl, index) => (
                <div
                  key={index}
                  className=" flex items-center justify-center w-full h-full"
                >
                  <img
                    src={imageUrl}
                    alt="petimage"
                    className=" max-w-full max-h-full object-contain"
                  />
                </div>
              ))}
          </Carousel>
        </div>
        <div className="mt-12 w-2/3"></div>
        <div className="w-5/6 h-32 mt-12 flex items-center rounded-2xl bg-[#ffffff]">
          <img
            className="ml-8 w-20 h-20 rounded-full"
            src={GetPeacockDetail && GetPeacockDetail.profileImgUrl}
          ></img>
          <div className="h-32 ml-8 flex flex-col items-start">
            <div className="mt-4 text-xs">
              {' '}
              {GetPeacockDetail && GetPeacockDetail.nickName}
            </div>
            <div className="mt-2 mb-2 font-bold text-3xl">
              {GetPeacockDetail && GetPeacockDetail.title}
            </div>
            <div className="flex items-center">
              <AiOutlineHeart />
              <span className="ml-4">
                {GetPeacockDetail && GetPeacockDetail.likes}
              </span>
              <AiOutlineComment className="ml-4" />
              <span className="ml-4">
                {GetPeacockDetail && GetPeacockDetail.commentCount}
              </span>
            </div>
          </div>
        </div>
        <div className="w-5/6 h-64 mt-12 rounded-2xl bg-[#ffffff]">
          <div className="ml-8 mt-8">
            {GetPeacockDetail && GetPeacockDetail.content}
          </div>
        </div>
        <div className="w-5/6 h-20 mt-12 mb-8 flex items-center rounded-2xl bg-[#ffffff]">
          <img className="ml-8 w-12 h-12 rounded-full" src={bulldog}></img>
          <span className="ml-4 font-bold text-2xl">염도둑과경찰</span>
          <div className="ml-8"> 펫칭입니다</div>
        </div>
      </div>
    </div>
  );
};

export default PeacockDetailComponent;
