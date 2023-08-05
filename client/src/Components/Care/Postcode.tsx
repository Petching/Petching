import React, { useState } from 'react';
import DaumPostcodeEmbed from '@actbase/react-daum-postcode';

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
    console.error();
  };
  const handleComplete = (data: AddressData) => {
    let fullAddress = data.address;
    if (data.addressType === 'R') {
      let extraAddress = '';
      if (data.bname !== '') extraAddress += data.bname;
      if (data.buildingName !== '')
        extraAddress +=
          extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }

    // 맨 앞 두 단어만 전달하려면 아래 코드를 사용하세요.
    const firstTwoWords = fullAddress.split(' ').slice(0, 2).join(' ');

    setAddress(firstTwoWords);
    onAddressSelected(firstTwoWords);
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
          className="w-[8.5rem] h-[2rem] text-sm text-center border-2"
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
