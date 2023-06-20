/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js,tsx}"],
  theme: {
    extend: {
      boxShadow: {
        custom: "0 0 10px 5px rgba(0, 0, 0, 0.1)",
      },
      colors: {
        customGreen: "#BADA7A",
        kakaoYellow: "#F7E600",
        kakaoText: "#3A1D1D",
      },
    },
  },
  plugins: [],
};
