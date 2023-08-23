import { useNavigate } from 'react-router-dom';
export const toCareListDetail = (postId: number) => {
  const navigate = useNavigate();
  navigate(`/carelistdetail/${postId}`);
};
