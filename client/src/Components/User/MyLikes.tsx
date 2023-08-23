import { useState } from 'react';
import { useGetUserLikes } from '../../Hook/useGetUserLikes';
import Card from '../Care/Card';
import PageBtns from './PageBtns';

const MyLikes: React.FC<{ userId: string }> = ({ userId }) => {
  const [page, setPage] = useState<number>(1);
  const { UserLikes } = useGetUserLikes(userId, page);
  console.log(UserLikes);

  if (UserLikes?.data.length === 0) {
    return (
      <div>
        <p>아직 좋아요 한 게시글이 없습니다.</p>
      </div>
    );
  }

  return (
    <div className="w-full">
      {/* <div className="w-full grid gap-4	grid-cols-1 md:grid-cols-3"> */}
      <div className="flex flex-wrap justify-center">
        {UserLikes &&
          UserLikes.data.map((ele, idx) => (
            // 자랑하기 컴포넌트로 변경할 것
            <Card
              key={idx}
              title={ele.title}
              locationTag={ele.locationTag}
              petSizes={ele.petSizes}
              nickName={ele.nickName}
              profileImgUrl={ele.profileImgUrl}
              imgUrls={ele.imgUrls}
              postId={ele.postId}
            />
          ))}
      </div>
      {UserLikes && <PageBtns data={UserLikes} page={page} setPage={setPage} />}
    </div>
  );
};

export default MyLikes;
