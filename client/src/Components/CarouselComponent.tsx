import React, { useState, useEffect, useRef } from 'react';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import doctor from '../assets/image/doctor.jpg';
import doctors from '../assets/image/doctors.jpg';
import pills from '../assets/image/pills.jpg';

const CarouselComponent: React.FC = () => {
  const slides = [
    {
      id: 1,
      image: doctor,
      legend: 'Doctor',
    },
    {
      id: 2,
      image: doctors,
      legend: 'Doctors',
    },
    {
      id: 3,
      image: pills,
      legend: 'Pills',
    },
  ];

  return (
    <Carousel
      showStatus={false}
      showIndicators={false}
      showThumbs={false}
      infiniteLoop={true}
    >
      {slides.map(slide => (
        <div key={slide.id}>
          <img src={slide.image} alt={slide.legend} />
        </div>
      ))}
    </Carousel>
  );
};
export default CarouselComponent;
