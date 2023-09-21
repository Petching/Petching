import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { MouseEventHandler, useRef } from 'react';

interface CarouselProps {
  imgUrls: string[];
  width: string;
  height: string;
}
interface ArrowProps {
  onClick: MouseEventHandler<HTMLButtonElement>;
}

const NextArrow = ({ onClick }: ArrowProps) => {
  return (
    <button
      onClick={onClick}
      type="button"
      className="absolute text-[1.7rem] text-[#b5b4b4] top-1/2 right-0 p-2 rounded-full shadow-md z-40 transform -translate-y-1/2"
    >
      {'>'}
    </button>
  );
};

const PrevArrow = ({ onClick }: ArrowProps) => {
  return (
    <button
      onClick={onClick}
      type="button"
      className="absolute text-[1.7rem] text-[#b5b4b4] top-1/2 left-0 p-2 rounded-full shadow-md z-40 transform -translate-y-1/2"
    >
      {'<'}
    </button>
  );
};
const Carousel: React.FC<CarouselProps> = ({ imgUrls, width, height }) => {
  const sliderRef = useRef<Slider>(null);

  const handleNextClick = () => {
    sliderRef.current?.slickNext();
  };

  const handlePrevClick = () => {
    sliderRef.current?.slickPrev();
  };

  const settings = {
    dots: false, // 캐러셀 밑에 ... 을 표시할지
    infinite: true, // 슬라이드가 끝까지 가면 다시 처음으로 반복
    speed: 500, // 속도
    autoplay: false, // 자동 재생
    autoplaySpeed: 3000, // 자동 재생 속도
    slidesToShow: 1, // 한 번에 보여줄 슬라이드 개수
    slidesToScroll: 1, // 한 번에 넘어가는 슬라이드 개수
    nextArrow: <NextArrow onClick={handleNextClick} />,
    prevArrow: <PrevArrow onClick={handlePrevClick} />,
  };

  return (
    <div>
      <Slider {...settings}>
        {imgUrls.map((imgUrl, index) => (
          <div key={index} className={`h-${height} w-${width}`}>
            <img
              className="h-full w-full"
              src={imgUrl}
              alt={`carousel-image-${index}`}
            />
          </div>
        ))}
      </Slider>
    </div>
  );
};
export default Carousel;
