import { useState } from 'react';
// import { useGetMyPostToCare } from '../../Hook/useGetMyPostToCare';

const MyPost: React.FC<{ userId: string }> = ({ userId }) => {
  const [category, setCategory] = useState<boolean>(true);
  // 돌봄리스트 / 자랑하기 버튼
  // 데이터 받아와서 뿌리기(각 게시판의 컴포넌트 사용)
  // const { MyPostToCare } = useGetMyPostToCare(userId);
  // console.log(MyPostToCare);

  return (
    <div className="w-full">
      <div className="">
        <button
          className={`${
            category && 'text-customHoverGreen'
          } hover:text-customHoverGreen mx-3`}
          onClick={() => setCategory(true)}
        >
          돌봄리스트
        </button>
        <button
          className={`${
            !category && 'text-customHoverGreen'
          } hover:text-customHoverGreen mx-3`}
          onClick={() => setCategory(false)}
        >
          자랑하기
        </button>
      </div>
      <div>
        {category ? (
          <>돌봄리스트 / 페이지네이션</>
        ) : (
          <>자랑하기 / 페이지네이션</>
        )}
      </div>
    </div>
  );
};

export default MyPost;
