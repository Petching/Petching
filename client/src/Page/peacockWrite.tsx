import React, { useState } from 'react';
import TextEditor from '../Components/Care/TextEditor';

const PeacockWrite = () => {
  const [title, setTitle] = useState('');

  return (
    <div className="ml-20">
      <div>
        <input
          className="w-4/5 h-16 mt-20 mb-4 border border-black"
          placeholder="제목을 입력하세요"
          value={title}
          onChange={e => setTitle(e.target.value)}
        />
      </div>
      <TextEditor title={title} />
      <button className="border">등록하기</button>
    </div>
  );
};

export default PeacockWrite;
