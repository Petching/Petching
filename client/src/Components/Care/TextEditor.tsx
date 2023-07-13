import React, { useRef, useState } from 'react';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

const Editor: React.FC = () => {
  const editorRef = useRef<any>(null);
  const [data, setData] = useState('');

  const handleInit = (editor: any) => {
    editorRef.current = editor;
    setData(editor.getData());
  };

  const handleChange = (event: any, editor: any) => {
    setData(editor.getData());
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
        onReady={(editor: any) => handleInit(editor)}
        onChange={(event: any, editor: any) => handleChange(event, editor)}
        data={data}
      />
    </div>
  );
};

export default Editor;
