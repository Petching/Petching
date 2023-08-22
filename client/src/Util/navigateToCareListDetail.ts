import { useNavigate } from 'react-router-dom';
const navigate = useNavigate();
export const toCareListDetail = (postId: number) => {
  navigate(`/carelistdetail/${postId}`);
};
