import { create } from 'zustand';

type LoginType = {
  isLogin: boolean;
  setLogin: () => void;
  setLogout: () => void;
};

// 로그인상태를 전역관리하기 위한 코드
const useLoginStore = create<LoginType>(set => ({
  isLogin: false,
  setLogin: () => set(() => ({ isLogin: true })),
  setLogout: () => set(() => ({ isLogin: false })),
}));
export default useLoginStore;
