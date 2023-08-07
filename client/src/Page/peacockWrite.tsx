import React from 'react';
import TextEditor from '../Components/Care/TextEditor';

const PeacockWrite = () => {
  return (
    <div className="ml-20">
      <div>
        <input
          className="w-4/5 h-16 mt-20 mb-4 border border-black"
          placeholder="제목을 입력하세요"
        />
      </div>
      <TextEditor />
      <button className="border">등록하기</button>
    </div>
  );
};

export default PeacockWrite;
