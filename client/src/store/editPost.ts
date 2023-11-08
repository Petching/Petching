import create from 'zustand';

interface Store {
  postToEdit: any;
  isEdit: boolean;
  setPostToEdit: (post: any) => void;
  setIsEdit: (isEdit: boolean) => void;
}

export const useStore = create<Store>(set => ({
  postToEdit: null,
  isEdit: false,
  setPostToEdit: post => set({ postToEdit: post }),
  setIsEdit: isEdit => set({ isEdit }),
}));
