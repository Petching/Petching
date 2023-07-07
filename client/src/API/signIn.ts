import axios from 'axios';

export const authenticate = async (email: string, password: number) => {
  try {
    const response = await axios.post(
      'https://5ad6-118-32-224-80.ngrok-free.app/users/login',
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
