import { User } from "../Util/types";

export const checkUser = async () => {
  // 백엔드 API 호출하여 DB에서 유저 정보 가져오기
  const response = await fetch("/api/check-user");
  const data: User = await response.json();
  return data;
};

export const checkNickname = async (nickname: string) => {
  const response = await fetch("/check-nickname", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ nickname }),
  });
  const data = await response.json();
  return data.isDuplicate;
};

export const checkEmail = async (email: string) => {
  const response = await fetch("/check-email", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email }),
  });
  const data = await response.json();
  return data.isDuplicate;
};
