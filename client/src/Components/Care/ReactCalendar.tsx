import React, { useState } from 'react';
import '@hassanmojab/react-modern-calendar-datepicker/lib/DatePicker.css';
import DatePicker, {
  DayRange,
} from '@hassanmojab/react-modern-calendar-datepicker';
import axios from 'axios';

const ReactCalendar = () => {
  const [selectedDayRange, setSelectedDayRange] = useState<DayRange>({
    from: null,
    to: null,
  });

  const handleSubmit = async (value: DayRange) => {
    if (value.from && value.to) {
      try {
        const response = await axios.post('https://server.petching.net/date', {
          startDate: value.from,
          endDate: value.to,
        });
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    }
  };

  const handleDayRangeChange = (value: DayRange) => {
    setSelectedDayRange(value);
    if (value.from && value.to) {
      handleSubmit(value);
    }
  };

  return (
    <div>
      <DatePicker
        value={selectedDayRange}
        onChange={handleDayRangeChange}
        inputPlaceholder="날짜를 선택하세요"
        shouldHighlightWeekends
      />
    </div>
  );
};

export default ReactCalendar;
