import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useStore from '../../store/useSearchStore';
import { GoSearch } from 'react-icons/go';
interface SearchInputProps {
  search: (search: string) => void;
}

const SearchInput: React.FC<SearchInputProps> = ({ search }) => {
  //   const [inputValue, setInputValue] = useState('');
  const navi = useNavigate();
  const { inputValue, setInputValue } = useStore();
  const handleSearch = () => {
    //이 값이 페이지가 옮겨지면서 저장되지 않고있는것같아.
    navi('/inquery/find');
    console.log(inputValue);
    search(inputValue);

    console.log('click');
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <div className="flex items-center justify-center relative">
      <GoSearch className="absolute bsolute left-3 top-[50%] transform -translate-y-[50%] text-gray-400 z-10" />
      <input
        className=" pl-8 w-full h-[100px] sm:w-full sm:h-[100px] md:w-[500px] md:h-[60px] rounded-2xl"
        placeholder="검색어를 입력해주세요"
        onChange={e => {
          setInputValue(e.target.value);
          console.log('Updated inputValue:', e.target.value);
        }}
        onKeyDown={handleKeyDown}
        // defaultValue={inputValue}
      ></input>
      <button
        onClick={handleSearch}
        className=" w-[55px] h-[42px] ml-2 rounded-xl bg-[#D5F5A9] hover:bg-customHoverGreen"
      >
        검색
      </button>
    </div>
  );
};

export default SearchInput;
