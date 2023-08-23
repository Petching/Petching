import React, { useEffect, useState } from 'react';
import Card from '../Components/Care/Card';
import ReactCalendar from '../Components/Care/ReactCalendar';
import Postcode from '../Components/Care/Postcode';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import InfiniteScroll from 'react-infinite-scroll-component';

interface Date {
  year: number;
  month: number;
  day: number;
}

const CareList = () => {
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [locationTag, setLocationTag] = useState('');
  const [cardData, setCardData] = useState<any[]>([]);
  const [page, setPage] = useState(0);

  const navigate = useNavigate();

  const onDateSelected = (value: any) => {
    if (value.from && value.to) {
      setStartDate({
        year: value.from.year,
        month: value.from.month,
        day: value.from.day,
      });
      setEndDate({
        year: value.to.year,
        month: value.to.month,
        day: value.to.day,
      });
    }
  };
  const onAddressSelected = (address: string) => {
    setLocationTag(address);
  };

  const handleSubmit = async () => {
    if (startDate && endDate && locationTag) {
      try {
        const response = await axios.get(
          'https://server.petching.net/careposts/search',
          {
            params: {
              locationTag,
              'startDate.day': startDate.day,
              'startDate.month': startDate.month,
              'startDate.year': startDate.year,
              'endDate.day': endDate.day,
              'endDate.month': endDate.month,
              'endDate.year': endDate.year,
            },
          },
        );
        setCardData(response.data.data);
      } catch (error) {
        console.error(error);
      }
    }
  };

  const toCareListPost = () => {
    navigate('/carelistpost', { state: { startDate, endDate, locationTag } });
  };

  const fetchMoreData = async () => {
    try {
      const response = await axios.get(
        `https://server.petching.net/careposts?page=${page}&size=10`,
      );
      setCardData(cardData.concat(response.data.data));
      setPage(page + 1);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchMoreData();
  }, []);

  return (
    <div className="text-[1.2rem]">
      <div className="flex flex-col w-full items-center bg-gray-100 ">
        <div>
          <div className="flex flex-col items-center">
            <div className="text-center leading-10 ">
              어느 지역을 찾으시나요?
            </div>
            <div className="flex items-center">
              <div>
                <Postcode onAddressSelected={onAddressSelected} />
              </div>
            </div>
          </div>
          <div className="flex flex-col items-center">
            <div className="text-center leading-10">언제 맡기시나요?</div>
            <div className="flex items-center">
              <div>
                <ReactCalendar onDateSelected={onDateSelected} />
              </div>
            </div>
          </div>
        </div>
        <div className="w-[15rem]">
          <div className="flex justify-end m-7">
            <button
              className="w-[7rem] h-7 bg-customGreen shadow-sm shadow-gray-400 rounded-full mr-1"
              onClick={handleSubmit}
            >
              검색
            </button>
            <button
              className="w-[7rem] h-7 bg-customGreen shadow-sm shadow-gray-400 rounded-full mr-1"
              onClick={toCareListPost}
            >
              글작성
            </button>
          </div>
        </div>
      </div>
      <div>
        <InfiniteScroll
          dataLength={cardData.length}
          next={fetchMoreData}
          hasMore={true}
          loader={<h4>Loading...</h4>}
        >
          <div className="flex justify-center">
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 gap-8">
              {cardData.map((cardData, index) => (
                <div key={index}>
                  <Card
                    key={index}
                    title={cardData.title}
                    locationTag={cardData.locationTag}
                    petSizes={cardData.petSizes}
                    nickName={cardData.nickName}
                    profileImgUrl={cardData.profileImgUrl}
                    imgUrls={cardData.imgUrls}
                    postId={cardData.postId}
                  />
                </div>
              ))}
            </div>
          </div>
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default CareList;
