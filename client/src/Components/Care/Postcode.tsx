import React, { useState } from 'react';
import axios from 'axios';
import DaumPostcodeEmbed from '@actbase/react-daum-postcode';

// 타입 정의 추가
interface AddressData {
  address: string;
  addressType: string;
  bname: string;
  buildingName: string;
}

interface PostcodeProps {
  onAddressSelected: (address: string) => void;
  onError?: () => void;
}

const Postcode: React.FC<PostcodeProps> = ({ onAddressSelected }) => {
  const [open, setOpen] = useState(false);
  const [address, setAddress] = useState('');

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };
  const handleError = () => {
    // 원하는 오류 처리 작업을 여기에 구현합니다.
  };
  const handleComplete = (data: AddressData) => {
    // 타입 적용
    let fullAddress = data.address;
    if (data.addressType === 'R') {
      let extraAddress = '';
      if (data.bname !== '') extraAddress += data.bname;
      if (data.buildingName !== '')
        extraAddress +=
          extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }

    setAddress(fullAddress);
    onAddressSelected(fullAddress);
    handleClose();
  };

  return (
    <div>
      <div className="relative">
        {open && (
          <div
            className="absolute top-0 right-0 bg-white z-10 p-1 m-1 text-black rounded-full cursor-pointer"
            onClick={handleClose}
          >
            X
          </div>
        )}
        <input
          className="w-[8.5rem] h-[2rem] text-sm"
          type="text"
          value={address}
          onClick={handleClick}
          readOnly
          placeholder="주소를 검색해주세요"
        />
        {open && (
          <DaumPostcodeEmbed
            onSelected={handleComplete}
            onError={handleError}
          />
        )}
      </div>
    </div>
  );
};

export default Postcode;
