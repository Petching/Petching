import { User } from "../Util/types";

export const checkUser = async () => {
  // 백엔드 API 호출하여 DB에서 유저 정보 가져오기
  const response = await fetch("/api/check-user");
  const data: User = await response.json();
  return data;
};
