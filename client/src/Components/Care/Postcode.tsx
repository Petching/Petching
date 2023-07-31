import React, { useState, useEffect } from 'react';
import axios from 'axios';
import DaumPostcodeEmbed from 'react-daum-postcode';

const Postcode = () => {
  const [open, setOpen] = useState(false);
  const [address, setAddress] = useState('');

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleComplete = async (data: any) => {
    let fullAddress = data.address;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress +=
          extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }

    try {
      const response = await axios.post('https://server.petching.net/address', {
        address: fullAddress,
      });
      console.log(response.data);
      setAddress(response.data.address);
      handleClose();
    } catch (error) {
      console.error(error);
    }
  };
  useEffect(() => {
    console.log(address);
  }, [address]);
  return (
    <>
      <div className="relative">
        <input
          className="w-[8.5rem] h-[2rem] text-sm"
          type="text"
          value={address}
          onClick={handleClick}
          readOnly
          placeholder="주소를 검색해주세요"
        />
        {open && <DaumPostcodeEmbed onComplete={handleComplete} />}
      </div>
    </>
  );
};

export default Postcode;
