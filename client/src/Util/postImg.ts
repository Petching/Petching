import axios from 'axios';

const BASE_URL = process.env.REACT_APP_BASE_URL;

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
