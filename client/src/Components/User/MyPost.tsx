/* eslint-disable */
import { useState } from 'react';
import { useGetMyPostToCare } from '../../Hook/useGetMyPostToCare';
import Card from '../Care/Card';
import { useGetMyPostToPeacock } from '../../Hook/useGetMyPostToPeacock';
import PageBtns from './PageBtns';

const MyPost: React.FC<{ userId: string }> = ({ userId }) => {
  const [category, setCategory] = useState<boolean>(true);
  const [page, setPage] = useState<number>(1);
  const { MyPostToCare } = useGetMyPostToCare(userId, page);
  const { MyPostToPeacock } = useGetMyPostToPeacock(userId, page);

  return (
    <div className="w-full">
      <div className="">
        <button
          className={`${
            category && 'text-hoverGreen'
          } hover:text-hoverGreen mx-3`}
          onClick={() => setCategory(true)}
        >
          돌봄리스트
        </button>
        <button
          className={`${
            !category && 'text-hoverGreen'
          } hover:text-hoverGreen mx-3`}
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
          <>
            {MyPostToPeacock &&
              MyPostToPeacock.data.map((ele, idx) => (
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
        )}
      </div>
      {category ? (
        <>
          {MyPostToCare && (
            <PageBtns data={MyPostToCare} page={page} setPage={setPage} />
          )}
        </>
      ) : (
        <>
          {MyPostToPeacock && (
            <PageBtns data={MyPostToPeacock} page={page} setPage={setPage} />
          )}
        </>
      )}
    </div>
  );
};

export default MyPost;
