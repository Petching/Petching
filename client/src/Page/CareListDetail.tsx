import Carousel from '../Components/Care/Carousel';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { getUserIdFromToken } from '../Util/getUserIdFromToken';

interface Date {
  year: number;
  month: number;
  day: number;
}

const CareListDetail = () => {
  const [title, setTitle] = useState('');
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [locationTag, setLocationTag] = useState('');
  const [petSizes, setPetSizes] = useState([]);
  const [content, setContent] = useState('');
  const [nickName, setNickName] = useState('');
  const [profileImgUrl, setProfileImgUrl] = useState('');
  const [imgUrls, setImgUrls] = useState<string[]>([]);
  const [postUserId, setPostUserId] = useState('');
  const [showEditButton, setShowEditButton] = useState(false);
  const navigate = useNavigate();
  const { postId } = useParams<{ postId: string }>();
  const Token = localStorage.getItem('ACCESS_TOKEN') || '';
  const userId = String(getUserIdFromToken(true, Token));
  console.log(userId);
  const fetchData = async () => {
    try {
      const response = await axios.get(
        `https://server.petching.net/careposts/${postId}`,
      );
      const data = response.data.data;

      setTitle(data.title);
      setStartDate(data.startDate);
      setEndDate(data.endDate);
      setLocationTag(data.locationTag);
      setPetSizes(data.petSizes);
      setContent(data.content);
      setNickName(data.nickName);
      setProfileImgUrl(data.profileImgUrl);
      setImgUrls(data.imgUrls);
      setPostUserId(data.userId);
      if (Number(userId) === data.userId) {
        setShowEditButton(true);
      }
    } catch (error) {
      console.error(error);
    }
  };

  const deletePost = async () => {
    const token = localStorage.getItem('ACCESS_TOKEN');
    try {
      await axios.delete(`https://server.petching.net/careposts/${postId}`, {
        headers: { Authorization: token },
      });
      navigate('/carelist');
      alert('게시물이 성공적으로 삭제되었습니다');
    } catch (error) {
      console.error(error);
      alert('게시물 삭제에 실패했습니다.');
    }
  };

  useEffect(() => {
    window.scrollTo(0, 0);
    fetchData();
  }, [postId]);

  return (
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen text-2xl">
      <div className="bg-[#F2F2F2] h-10 mt-[4rem]"></div>
      <div className="mt-[1rem]">
        <div className="mx-auto w-[40rem] h-96">
          <Carousel imgUrls={imgUrls} width="40rem" height="96" />
        </div>
        <div className="bg-[#F2F2F2] text-center">
          <div className="bg-white mx-auto h-[10rem] rounded-md mt-5 w-[40rem]">
            <div className="flex items-center mt-5">
              <img
                className="w-20 ml-4 mr-[8.5rem] rounded-full"
                src={profileImgUrl}
              ></img>
              <div>
                <div>{nickName}</div>
                <div>{title}</div>
                <div>
                  {startDate && endDate
                    ? `${startDate.year}-${startDate.month}-${startDate.day} ~ ${endDate.year}-${endDate.month}-${endDate.day}`
                    : '날짜 정보를 불러오는 중...'}
                </div>
                <button className="bg-customGreen rounded-md">
                  {locationTag}
                </button>
                {petSizes.map((size: any, idx: any) => (
                  <button key={idx} className="bg-customGreen rounded-md mx-2">
                    {size}
                  </button>
                ))}
              </div>
            </div>
          </div>
          <div className="bg-white mx-auto h-[9rem] rounded-md mt-5 w-[40rem]">
            {content}
          </div>
          <div className="mt-5">
            <button className="bg-customPink rounded-md w-48 h-20">
              문의 하기
            </button>
            {showEditButton && (
              <button className="bg-customGreen rounded-md w-48 h-20 ml-5">
                수정하기
              </button>
            )}
            <button
              className="bg-[#131342] text-white rounded-md w-48 h-20 ml-5"
              onClick={deletePost}
            >
              삭제하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CareListDetail;
