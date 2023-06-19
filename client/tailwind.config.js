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
      },
    },
  },
  plugins: [],
};
