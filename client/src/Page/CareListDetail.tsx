import Carousel from '../Components/Care/Carousel';
import { useParams } from 'react-router-dom';
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

  useEffect(() => {
    fetchData();
  }, [postId]);

  return (
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen text-xl">
      <div>
        <div className="mx-auto w-60">
          <Carousel imgUrls={imgUrls} />
        </div>
        <div className="bg-[#F2F2F2] text-center">
          <div className="bg-white mt-5">
            <img className="w-20 " src={profileImgUrl}></img>
            <div>{nickName}</div>
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
          <button className="bg-customPink">문의 하기</button>
        </div>
      </div>
    </div>
  );
};

export default CareListDetail;
