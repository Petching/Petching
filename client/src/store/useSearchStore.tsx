import create from 'zustand';
type StoreState = {
  inputValue: string;
  setInputValue: (value: string) => void;
};

const useStore = create<StoreState>(set => ({
  inputValue: '',
  setInputValue: (value: string) => set({ inputValue: value }),
}));

export default useStore;
