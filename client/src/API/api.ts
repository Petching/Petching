import axios from 'axios';

export const Axios = axios.create({
  baseURL: process.env.API_SERVER,
  timeout: 10000,
  withCredentials: true,
});
//request 중간
axios.interceptors.request.use(
  function (config) {
    const accessToken = localStorage.getItem('ACCESS_TOKEN');
    if (accessToken) {
      config.headers['Authorization'] = `Bearer ${accessToken}`;
    }
    return config;
  },
  function (error) {
    return Promise.reject(error);
  },
);
