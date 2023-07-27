/* eslint-disable */
import axios from 'axios';
const BASE_URL = process.env.REACT_APP_BASE_URL;

export const authenticate = async (email: string, password: string) => {
  try {
    const response = await axios.post(
      `https://server.petching.net/users/login`,
      { email, password },
      {
        headers: {
          'Content-Type': 'application/json',
        },
      },
    );

    if (response.status === 200) {
      if (response.headers['authorization']) {
        const token = response.headers['authorization'];

        localStorage.setItem('TOKEN', token);
        return true;
      }
    }
  } catch (error) {
    console.error(error);
    throw error;
  }
};
