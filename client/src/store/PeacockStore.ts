import { create } from 'zustand';

interface PeacockState {
  editorContent: string;
  setEditorContent: (content: string) => void;
}

export const usePeacockStore = create<PeacockState>(set => ({
  editorContent: '',
  setEditorContent: content => set({ editorContent: content }),
}));
