/** @type {import('tailwindcss').Config} */
/* eslint-disable */
module.exports = {
  content: ['./src/**/*.{html,js,tsx}'],
  theme: {
    extend: {
      boxShadow: {
        custom: '0 0 10px 5px rgba(0, 0, 0, 0.1)',
      },
      fontFamily: {
        custom: ['HakgyoansimWoojuR', 'sans'],
      },
      colors: {
        customGreen: '#C3EBDB',
        customHoverGreen: '#C1F3DE',
        customPink: '#FFE2E2',
        customWhite: '#F6F6F6',
        customBlue: '#BAD7DF',
        kakaoYellow: '#F7E600',
        kakaoText: '#3A1D1D',
        hoverGreen: '#52D2B3',
        InqueryMain: '#94BBC2',
        InqueryComponentButton: '#F5F5EB',
        InqueryPopular: '#98CDF5',
      },
    },
  },
  plugins: [],
};
