import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import '@toast-ui/editor/dist/i18n/ko-kr';
import axios from 'axios';

export type HookMap = {
  addImageBlobHook?: (blob: Blob | File, callback: HookCallback) => void;
};
type HookCallback = (url: string, text?: string) => void;

function TextEditor() {
  const BASE_URL = process.env.REACT_APP_BASE_URL;

  async function uploadImage(blob: any) {
    const files = new FormData();
    files.append('files', blob);

    try {
      const response = await axios.post(
        `${BASE_URL}/api/s3/uploads?uploadTo=boards`,
        files,
      );
      return response.data.url;
    } catch (error) {
      console.error(error);
    }
  }

  const onUploadImage = async (blob: any, callback: any) => {
    const url = await uploadImage(blob);
    callback(url, 'alt text');
    return false;
  };

  return (
    <Editor
      initialValue="hello react editor world!"
      previewStyle="vertical"
      height="200px"
      initialEditType="wysiwyg"
      useCommandShortcut={false}
      language="ko-KR"
      hooks={{
        addImageBlobHook: onUploadImage,
      }}
    />
  );
}

export default TextEditor;
