const Peacock = () => {
  return (
    <div className='w-full h-full flex justify-center items-center'>
      <div className='w-1/5 -mt-20'>
        <p className='text-center text-xl'>
          당신의 반려동물을 <br />
          자랑하고 <br />
          다른 사람들의 반려동물을 <br />
          구경하세요
        </p>
        <button className='w-full h-16 mt-10 bg-customGreen text-xl hover:scale-90 transition-all rounded'>
          자랑하러 가기
        </button>
      </div>
      <div className='flex'>
        <div className='w-60 h-60 bg-black relative -top-10 left-32 rounded' />
        <div className='w-60 h-60 bg-gray-500 relative top-10 left-20 rounded' />
        <div className='w-60 h-60 bg-black relative -top-10 left-10 rounded' />
        <div className='w-60 h-60 bg-gray-500 relative top-10 rounded' />
      </div>
    </div>
  );
};

export default Peacock;
