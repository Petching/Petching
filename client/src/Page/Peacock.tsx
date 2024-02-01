import {
  CarouselComponent,
  Square,
} from '../Components/Peacock/PeacockComponent';
import React from 'react';
const Peacock = () => {
  return (
    <div className="flex flex-col items-center">
      <Square />
      <div className="flex flex-row justify-center">
        <CarouselComponent />
      </div>
    </div>
  );
};

export default Peacock;
