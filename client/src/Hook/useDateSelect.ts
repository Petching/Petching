import { useState } from 'react';

interface Date {
  year: number;
  month: number;
  day: number;
}

const useDateSelect = () => {
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);

  const onDateSelected = (value: any) => {
    if (value.from && value.to) {
      setStartDate({
        year: value.from.year,
        month: value.from.month,
        day: value.from.day,
      });
      setEndDate({
        year: value.to.year,
        month: value.to.month,
        day: value.to.day,
      });
    }
  };

  return { startDate, endDate, onDateSelected };
};

export default useDateSelect;
