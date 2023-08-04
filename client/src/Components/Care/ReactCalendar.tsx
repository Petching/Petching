import React, { useState } from 'react';
import '@hassanmojab/react-modern-calendar-datepicker/lib/DatePicker.css';
import DatePicker, {
  DayRange,
} from '@hassanmojab/react-modern-calendar-datepicker';

interface ReactCalendarProps {
  onDateSelected: (value: DayRange) => void;
}
const ReactCalendar: React.FC<ReactCalendarProps> = ({ onDateSelected }) => {
  const [selectedDayRange, setSelectedDayRange] = useState<DayRange>({
    from: null,
    to: null,
  });

  const handleDayRangeChange = (value: DayRange) => {
    setSelectedDayRange(value);
    onDateSelected(value);
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
