import React, { useState } from 'react';
import { Editor } from 'react-draft-wysiwyg';
import { EditorState, convertToRaw } from 'draft-js';
import 'react-draft-wysiwyg/dist/react-draft-wysiwyg.css';
import draftjsToHtml from 'draftjs-to-html';

const Draft = () => {
  const [editorState, setEditorState] = useState(EditorState.createEmpty());
  const [htmlString, setHtmlString] = useState('');

  const updateTextDescription = async (state: EditorState) => {
    await setEditorState(state);
    const html = draftjsToHtml(convertToRaw(editorState.getCurrentContent()));
    setHtmlString(html);
  };

  const uploadCallback = () => {
    console.log('이미지 업로드');
  };

  return (
    <>
      <div>draft</div>
      <div className="w-full">
        <Editor
          placeholder="게시글을 작성해주세요"
          editorState={editorState}
          onEditorStateChange={updateTextDescription}
          toolbar={{
            image: { uploadCallback: uploadCallback },
          }}
          localization={{ locale: 'ko' }}
          editorStyle={{
            height: '400px',
            width: '100%',
            border: '3px solid lightgray',
            padding: '20px',
          }}
        />
      </div>
      <div className="w-full flex">
        <div
          className="w-1/2 p-5 mt-5 border-2 border-gray-400"
          dangerouslySetInnerHTML={{ __html: htmlString }}
        />
        <div className="w-1/2 p-5 mt-5 border-2 border-gray-400">
          {htmlString}
        </div>
      </div>
    </>
  );
};

export default Draft;
