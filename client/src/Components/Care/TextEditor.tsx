import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import { useState, useRef } from 'react';
import { Axios } from '../../API/api';

export type HookMap = {
  addImageBlobHook?: (blob: Blob | File, callback: HookCallback) => void;
};
type HookCallback = (url: string, text?: string) => void;

interface TextEditorProps {
  title: string;
}

//title props에 대한 TextEditorProps추가
const TextEditor = ({ title }: TextEditorProps) => {
  const BASE_URL = process.env.REACT_APP_BASE_URL;
  const [imgUrls, setImgUrls] = useState<string[]>();
  const editorRef = useRef<Editor | null>(null);

  async function uploadImage(blob: any) {
    const files = new FormData();
    files.append('files', blob);
    console.log(blob);
    try {
      const response = await Axios.post(
        `${BASE_URL}/api/s3/uploads?uploadTo=boards`,
        files,
      );
      console.log(response.data);
      console.log(response.data);
      return response.data.data[0].uploadFileUrl;
    } catch (error) {
      console.error(error);
    }
  }

  const onUploadImage = async (blob: any, callback: any) => {
    const url = await uploadImage(blob);
    console.log(url);
    callback(url, 'alt text');
    //null값 예외처리 추가
    setImgUrls(prevUrls => (prevUrls ? [...prevUrls, url] : [url]));
    return false;
  };
  const handleSubmit = async () => {
    if (editorRef.current) {
      const instance = editorRef.current.getInstance();
      const html = instance.getMarkdown();
      console.log(html);

      // 이미지 제외한 텍스트 추출
      const text = html.replace(/!\[[^\]]*\]\([^)]*\)/g, '');
      console.log(text);

      const requestBody = {
        title,
        content: text,
        imgUrls: imgUrls,
      };
      // Axios를 사용하여 POST 요청 전송
      try {
        const response = await Axios.post(`${BASE_URL}/boards`, requestBody);
        console.log(response.data);
      } catch (error) {
        console.error(error);
      }
    }
  };

  return (
    <>
      <Editor
        ref={editorRef}
        hideModeSwitch={true}
        initialValue="내용을 입력하세요"
        previewStyle="vertical"
        height="500px"
        useCommandShortcut={false}
        language="ko-KR"
        initialEditType="markdown"
        hooks={{
          addImageBlobHook: onUploadImage,
        }}
      />
      <button
        onClick={handleSubmit}
        className="mt-4 bg-customGreen hover:bg-customHoverGreen text-gray-700 font-bold py-2 px-4 rounded duration-300 hover:scale-110 ease-in-out"
      >
        Submit
      </button>
    </>
  );
};

export default TextEditor;
