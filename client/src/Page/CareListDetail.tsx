import Carousel from '../Components/Care/Carousel';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState } from 'react';

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
  const navigate = useNavigate();
  const { postId } = useParams<{ postId: string }>();

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
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen text-xl">
      <div>
        <div className="mx-auto w-[30rem] h-80">
          <Carousel imgUrls={imgUrls} width="30rem" height="80" />
        </div>
        <div className="bg-[#F2F2F2] text-center">
          <div className="bg-white mt-5">
            <div className="bg-white mt-5 items-center justify-center">
              <img className="w-10 " src={profileImgUrl}></img>
              <div>{nickName}</div>
            </div>
            <div>{title}</div>
            <div>
              {startDate && endDate
                ? `${startDate.year}-${startDate.month}-${startDate.day} ~ ${endDate.year}-${endDate.month}-${endDate.day}`
                : '날짜 정보를 불러오는 중...'}
            </div>
            <button className="bg-customGreen rounded-full">
              {locationTag}
            </button>
            {petSizes.map((size: any, idx: any) => (
              <button key={idx} className="bg-customGreen rounded-full mx-2">
                {size}
              </button>
            ))}
            <div>{content}</div>
          </div>
          <div>
            <button className="bg-customPink">문의 하기</button>
            <button className="bg-black text-white" onClick={deletePost}>
              삭제하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CareListDetail;
