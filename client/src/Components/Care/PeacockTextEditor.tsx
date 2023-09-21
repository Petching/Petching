import { useRef } from 'react';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { usePeacockStore } from '../../store/PeacockStore';

const PeacockTextEditor = () => {
  const editorRef = useRef<any>(null);
  const editorContent = usePeacockStore(state => state.editorContent);

  const handleChange = (event: any, editor: any) => {
    const content = editor.getData();
    usePeacockStore.setState({ editorContent: content }); // 상태 업데이트
  };

  return (
    <div className="w-full">
      <CKEditor
        editor={ClassicEditor}
        config={{
          toolbar: [
            'bold',
            'italic',
            'link',
            'bulletedList',
            'numberedList',
            'blockQuote',
          ],
        }}
        onReady={(editor: any) => (editorRef.current = editor)}
        onChange={(event: any, editor: any) => handleChange(event, editor)}
        data={editorContent} // 에디터에 상태를 설정합니다.
      />
    </div>
  );
};

export default PeacockTextEditor;
