import Carousel from '../Components/Care/Carousel';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { useEffect, useState } from 'react';

const CareListDetail = () => {
  const [title, setTitle] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [locationTag, setLocationTag] = useState('');
  const [petSizes, setPetSizes] = useState([]);
  const [content, setContent] = useState('');
  const [nickName, setNickName] = useState('');
  const [profileImgUrl, setProfileImgUrl] = useState('');
  const [imgUrls, setImgUrls] = useState<string[]>([]);

  const { id } = useParams<{ id: string }>();

  const fetchData = async () => {
    try {
      const response = await axios.get(
        `https://server.petching.net/careposts/${id}`,
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
  }, [id]);

  return (
    <div className="bg-[#F2F2F2] w-full h-full min-h-screen text-xl">
      <div>
        <Carousel imgUrls={imgUrls} />
        <div className="bg-[#F2F2F2] text-center">
          <div className="bg-white mt-5">
            <img className="w-20 " src={profileImgUrl}></img>
            <div>{nickName}</div>
            <div>{title}</div>
            <div>
              {startDate}~{endDate}
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
