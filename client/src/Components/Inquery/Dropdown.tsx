import React, { useState, useRef } from 'react';

const Dropdown = () => {
  const [isOpen, setIsOpen] = useState(false);
  const toggleDropdown = () => setIsOpen(!isOpen);

  type OptionType = {
    value: string;
    label: string;
  };

  const options: OptionType[] = [
    { value: '-', label: '-' },
    { value: '운영', label: '운영관련' },
    { value: '찾아오시는 길', label: '찾아오시는 길' },
    { value: '펫', label: '펫' },
    { value: '펫시터', label: '펫시터' },
  ];

  const [selectedOption, setSelectedOption] = useState<OptionType>(options[0]);

  const dropdownRef = useRef<HTMLDivElement>(null);

  const handleClickOutside = (event: MouseEvent) => {
    if (
      dropdownRef.current &&
      !dropdownRef.current.contains(event.target as Node)
    ) {
      setIsOpen(false);
    }
  };

  React.useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <div className="relative z-10 " ref={dropdownRef}>
      <button
        onClick={toggleDropdown}
        className="border p-2 rounded w-full lg:w-[700px] text-2xl"
      >
        {selectedOption.label}
      </button>
      {isOpen && (
        <div className="absolute mt-2 w-full lg:w-[700px] bg-gray-200 border rounded shadow-md">
          {options.map(option => (
            <div
              key={option.value}
              className="cursor-pointer hover:bg-gray-100 p-2"
              onClick={() => {
                setSelectedOption(option);
                setIsOpen(false);
              }}
            >
              {option.label}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Dropdown;
