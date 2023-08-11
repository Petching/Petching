import axios from 'axios';

const BASE_URL = process.env.REACT_APP_API_SERVER;

// 이미지 url 요청할 때 사용
export const postImgHandler = async (
  img: File,
  uploadTo: 'boards' | 'profiles' | 'careposts' | 'mypets',
) => {
  const files = new FormData();
  files.append('files', img);
  return await axios
    .post(`${BASE_URL}/api/s3/uploads?uploadTo=${uploadTo}`, files)
    .then(res => res.data.data[0].uploadFileUrl);
};

export const deleteImgHandler = async (
  url: string,
  from: 'boards' | 'profiles' | 'careposts' | 'mypets',
) => {
  await axios.delete(`${BASE_URL}/api/s3/delete?from=${from}&url=${url}`);
};
