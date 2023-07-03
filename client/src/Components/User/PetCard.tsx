const PetCard = () => {
  return (
    <div className="flex border p-4 rounded relative">
      <div className="w-32 h-32 rounded overflow-hidden mr-3">
        <img
          src="https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_1280.jpg"
          alt="반려동물의 이미지"
          className="w-full h-full"
        />
      </div>
      <div>
        <p>이름</p>
        <p>종(세부종)</p>
        <p>성별</p>
        <p>나이</p>
        <p>특이사항(기타사항)</p>
      </div>
      <div className="absolute top-4 right-4">
        <button className="mr-2 hover:text-customGreen">수정</button>
        <button className="hover:text-red-600">삭제</button>
      </div>
    </div>
  );
};

export default PetCard;
