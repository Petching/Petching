import React, { useState } from 'react';
import 'react-modern-calendar-datepicker/lib/DatePicker.css';
import { Calendar, DayRange } from 'react-modern-calendar-datepicker';

const DatePicker = () => {
  const defaultFrom = {
    year: 2023,
    month: 7,
    day: 28,
  };

  const defaultTo = {
    year: 2023,
    month: 7,
    day: 29,
  };

  const defaultRange: DayRange = {
    from: defaultFrom,
    to: defaultTo,
  };

  const [selectedDayRange, setSelectedDayRange] = useState(defaultRange);
  return (
    <Calendar
      value={selectedDayRange}
      onChange={setSelectedDayRange}
      shouldHighlightWeekends
    />
  );
};
export default DatePicker;
