import React, { useState } from 'react';
import Calendar from 'react-calendar';

const DatePicker = () => {
  const [value, onChange] = useState(new Date());
  return <Calendar></Calendar>;
};
export default DatePicker;
