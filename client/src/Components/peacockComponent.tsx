import React, { useState } from 'react';
import pit from '../Style/kakaoLogo.png';
import CarouselComponent from './CarouselComponent';
import LikeComponent from './LikeComponent';

const PeacockComponent = () => {
  return (
    <div className="h-96 w-72 bg-[#f2f2f2]">
      <CarouselComponent />
      <div className="mt-3 text-center">노가르시아</div>
      <div className="flex items-center ml-2">
        <img className="w-16 h-16" src={pit} alt="logo" />
        <span>sdasd</span>
      </div>
      <LikeComponent />
    </div>
  );
};

export default PeacockComponent;
