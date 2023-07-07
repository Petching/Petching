import axios from 'axios';

export const authenticate = async (email: string, password: string) => {
  try {
    const response = await axios.post(
      '/api/authenticate',
      { email, password },
      {
        headers: {
          'Content-Type': 'application/json',
          'ngrok-skip-browser-warning': '69420',
        },
      },
    );

    return response.status === 200;
  } catch (error) {
    // Handle error if the request fails
    console.error(error);
    throw error;
  }
};
