import { PeacockComponent, Square } from '../Components/peacockComponent';
import React from 'react';
const Peacock = () => {
  return (
    <div className="flex flex-col items-center">
      <Square />
      <div className="flex flex-wrap justify-center">
        <PeacockComponent />
      </div>
    </div>
  );
};

export default Peacock;
