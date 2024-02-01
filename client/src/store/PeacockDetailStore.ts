import { create } from 'zustand';

interface BoardIdState {
  boardId: number;
  setBoardId: (content: number) => void;
}
// zustand 스토어 생성
export const usePeacockBoardIdStore = create<BoardIdState>(set => ({
  boardId: Number(localStorage.getItem('peacockBoardId')) || 0,
  setBoardId: content => {
    set({ boardId: content });
    localStorage.setItem('peacockBoardId', String(content));
  },
}));
