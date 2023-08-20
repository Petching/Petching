import { useState } from 'react';
import MyPets from '../Components/User/MyPets';
import UserInfo from '../Components/User/UserInfo';
import MyPost from '../Components/User/MyPost';
import MyLikes from '../Components/User/MyLikes';
import { useParams } from 'react-router-dom';

const User = () => {
  const { userId } = useParams() as { userId: string };

  const [category, setCategory] = useState('반려동물');
  return (
    <div className="flex justify-center w-full flex-col items-center">
      <UserInfo userId={userId} />
      <div className="flex justify-center items-center">
        <button
          onClick={() => setCategory('반려동물')}
          className={`mx-5 ${
            category === '반려동물' &&
            'text-customGreen  decoration-wavy underline'
          }`}
        >
          반려동물
        </button>
        <button
          onClick={() => setCategory('작성')}
          className={`mx-5 ${
            category === '작성' && 'text-customGreen  decoration-wavy underline'
          }`}
        >
          작성한 게시글
        </button>
        <button
          onClick={() => setCategory('좋아요')}
          className={`mx-5 ${
            category === '좋아요' &&
            'text-customGreen  decoration-wavy underline'
          }`}
        >
          좋아요 게시글
        </button>
      </div>
      <div className="flex justify-center items-center mt-10 w-9/12">
        {category === '반려동물' && <MyPets userId={userId} />}
        {category === '작성' && <MyPost userId={userId} />}
        {category === '좋아요' && <MyLikes userId={userId} />}
      </div>
    </div>
  );
};

export default User;
