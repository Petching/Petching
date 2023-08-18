/* eslint-disable */
import { useState } from 'react';
import { useGetMyPostToCare } from '../../Hook/useGetMyPostToCare';
import Card from '../Care/Card';

const MyPost: React.FC<{ userId: string }> = ({ userId }) => {
  const [category, setCategory] = useState<boolean>(true);
  const [page, setPage] = useState<number>(1);
  const { MyPostToCare } = useGetMyPostToCare(userId, page);
  console.log(MyPostToCare);

  const pageNumber = Array(MyPostToCare?.pageInfo.totalPages)
    .fill(0)
    .map((_, index) => index + 1);
  const goFirstPageHandler = () => {
    setPage(1);
  };

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
      {/* <div className="w-full grid gap-4	grid-cols-1 md:grid-cols-3"> */}
      <div className="flex flex-wrap justify-center">
        {category ? (
          <>
            {MyPostToCare &&
              MyPostToCare.data.map((ele, idx) => (
                // 추후 postId로 감싸는 div 추가할 것
                <Card
                  key={idx}
                  title={ele.title}
                  locationTag={ele.locationTag}
                  petSize={ele.petSize}
                  nickName={ele.nickName}
                  profileImgUrl={ele.profileImgUrl}
                  imgUrls={ele.imgUrls}
                />
              ))}
          </>
        ) : (
          <>자랑하기 / 페이지네이션</>
        )}
      </div>
      <div className="mt-10 mb-20 flex justify-center">
        {pageNumber.length !== 0 ? (
          pageNumber.map(number => (
            <button
              key={number}
              className={`w-5 h-10 mx-1 rounded ${
                number === page && 'text-customHoverGreen'
              } hover:text-customHoverGreen`}
              onClick={() => setPage(number)}
            >
              {number}
            </button>
          ))
        ) : (
          <button
            className="w-5 h-10 mx-1 rounded border"
            onClick={goFirstPageHandler}
          >
            1
          </button>
        )}
      </div>
    </div>
  );
};

export default MyPost;
