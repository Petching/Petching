import React, { useState } from 'react';
import '@hassanmojab/react-modern-calendar-datepicker/lib/DatePicker.css';
import DatePicker, {
  DayRange,
} from '@hassanmojab/react-modern-calendar-datepicker';

interface ReactCalendarProps {
  onDateSelected: (value: DayRange) => void;
  value?: DayRange;
}
const ReactCalendar: React.FC<ReactCalendarProps> = ({
  onDateSelected,
  value,
}) => {
  const [selectedDayRange, setSelectedDayRange] = useState<DayRange>({
    from: null,
    to: null,
  });

  const handleDayRangeChange = (value: DayRange) => {
    setSelectedDayRange(value);
    onDateSelected(value);
  };

  const renderCustomInput = ({ ref }: any) => (
    <input
      readOnly
      ref={ref}
      value={
        selectedDayRange.from && selectedDayRange.to
          ? `${selectedDayRange.from.year}/${selectedDayRange.from.month}/${selectedDayRange.from.day} - ${selectedDayRange.to.year}/${selectedDayRange.to.month}/${selectedDayRange.to.day}`
          : ''
      }
      placeholder="날짜를 선택하세요"
      style={{
        textAlign: 'center',
        padding: '1rem ',
        width: '20rem',
        height: '4rem',
        fontSize: '1.2rem',
        border: '1px solid #adaaaa',
        borderRadius: '10px',
      }}
      className="my-custom-input-class" // a styling class
    />
  );

  return (
    <div>
      <DatePicker
        value={value || selectedDayRange}
        onChange={handleDayRangeChange}
        inputPlaceholder="날짜를 선택하세요"
        renderInput={renderCustomInput}
        shouldHighlightWeekends
      />
    </div>
  );
};

export default ReactCalendar;
