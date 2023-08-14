const SearchInput = () => {
  return (
    <div className="flex ">
      <input
        className=" w-full h-[100px] sm:w-full sm:h-[100px] md:w-[500px] md:h-[60px] rounded-3xl"
        placeholder="검색어를 입력해주세요"
      />
      <button className="w-[60px] ml-2 rounded-2xl bg-customPink hover:bg-customHoverGreen">
        검색
      </button>
    </div>
  );
};

export default SearchInput;
