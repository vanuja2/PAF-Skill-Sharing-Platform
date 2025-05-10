/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,jsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          50: '#e0fcf6',
          100: '#b3f7e7',
          200: '#80f2d8',
          300: '#4dedc9',
          400: '#1AEDB3', // Main brand color
          500: '#17c79a',
          600: '#13a282',
          700: '#0f7c69',
          800: '#0c574f',
          900: '#083136',
        },
      },
      aspectRatio: {
        'w-16': '16',
        'h-9': '9',
      },
    },
  },
  plugins: [
    require('@tailwindcss/aspect-ratio'),
  ],
}
