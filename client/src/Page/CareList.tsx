import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { MouseEventHandler, useRef } from 'react';
import maltese1 from '../Style/maltese1.jpg';
import maltese2 from '../Style/maltese2.jpg';
import maltese3 from '../Style/maltese3.jpg';
import maltese4 from '../Style/maltese4.jpg';
import maltese5 from '../Style/maltese5.jpg';

interface ArrowProps {
  onClick: MouseEventHandler<HTMLButtonElement>;
}

const NextArrow = ({ onClick }: ArrowProps) => {
  return (
    <button
      onClick={onClick}
      type="button"
      className="absolute top-1/2 right-0 bg-white p-2 rounded-full shadow-md"
    >
      ▷
    </button>
  );
};

const PrevArrow = ({ onClick }: ArrowProps) => {
  return (
    <button
      onClick={onClick}
      type="button"
      className="absolute top-1/2 left-0 bg-white p-2 rounded-full shadow-md"
    >
      ◁
    </button>
  );
};
const Carousel = () => {
  const sliderRef = useRef<Slider>(null);

  const handleNextClick = () => {
    sliderRef.current?.slickNext();
  };

  const handlePrevClick = () => {
    sliderRef.current?.slickPrev();
  };

  const settings = {
    dots: true, // 캐러셀 밑에 ... 을 표시할지
    infinite: true, // 슬라이드가 끝까지 가면 다시 처음으로 반복
    speed: 3000, // 속도
    autoplay: true, // 자동 재생
    autoplaySpeed: 3000, // 자동 재생 속도
    slidesToShow: 1, // 한 번에 보여줄 슬라이드 개수
    slidesToScroll: 1, // 한 번에 넘어가는 슬라이드 개수
    nextArrow: <NextArrow onClick={handleNextClick} />,
    prevArrow: <PrevArrow onClick={handlePrevClick} />,
  };

  return (
    <div>
      <Slider {...settings}>
        <div className="h-56 w-80 bg-sky-100">
          <img src={maltese1} />
        </div>
        <div className="h-56 w-80 bg-pink-100">
          <img src={maltese2} />
        </div>
        <div className="h-56 w-80 bg-yellow-100">
          <img src={maltese3} className="w-20" />
        </div>
        <div className="h-56 w-80 bg-red-100">
          <img src={maltese4} />
        </div>
        <div className="h-56 w-80 bg-purple-100">
          <img src={maltese5} />
        </div>
      </Slider>
    </div>
  );
};
const CareList = () => {
  return (
    <div>
      <Carousel></Carousel>
    </div>
  );
};

export default CareList;
