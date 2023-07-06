import React, { useState } from 'react';
import { useMutation } from 'react-query';
import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';
import { AiOutlineComment } from 'react-icons/ai';

const LikeComponent = () => {
  const [count, setCount] = useState(0);
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
      <button onClick={handleLike}>
        {isLiked ? <AiFillHeart color="red" /> : <AiOutlineHeart />}
      </button>
      <AiOutlineComment />
      <span>{count}</span>
    </div>
  );
};

export default LikeComponent;
