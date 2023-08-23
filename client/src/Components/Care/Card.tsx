import React from 'react';
import Carousel from './Carousel';
import { CardProps } from '../../Util/types';

const Card: React.FC<CardProps> = ({
  title,
  locationTag,
  petSizes,
  nickName,
  profileImgUrl,
  imgUrls,
  onCardClick,
}) => {
  return (
    <div className="m-4">
      <div className=" text-center rounded-md w-[18rem] h-[26rem] pt-6 bg-white shadow-lg shadow-gray-400">
        <div className="mx-auto w-60">
          <Carousel imgUrls={imgUrls} />
        </div>
        <div onClick={onCardClick}>
          <div>
            <button className="w-[7rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full mr-1">
              {locationTag}
            </button>
            {petSizes.map(size => (
              <button
                key={size}
                className="w-[4rem] h-7 bg-white shadow-sm shadow-gray-400 rounded-full ml-1"
              >
                {size}
              </button>
            ))}
          </div>
          <div className="mt-5">{title}</div>
          <div className="flex justify-center items-center">
            <img
              className="w-8 h-8 rounded-full mr-2"
              src={profileImgUrl}
              alt={nickName}
            />
            <div className="mr-2">{nickName}</div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Card;
