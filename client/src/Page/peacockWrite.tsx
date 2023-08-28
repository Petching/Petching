import PeacockTextEditor from '../Components/Care/PeacockTextEditor';
import React, { useRef, useState } from 'react';
import { useCreateBoard, BoardData } from '../Hook/usePostPeacock';
import { usePeacockStore } from '../store/PeacockStore';
import Board from '../Components/Care/Board';
import { postImgHandler } from '../Util/postImg';

const PeacockWrite = () => {
  const editorContent = usePeacockStore(state => state.editorContent);
  const [title, setTitle] = useState('');
  const createBoardMutation = useCreateBoard();
  const [imageSrc1, setImageSrc1]: any = useState<File>();
  const [imageSrc2, setImageSrc2]: any = useState<File>();
  const [imageSrc3, setImageSrc3]: any = useState<File>();
  const [imageSrc4, setImageSrc4]: any = useState<File>();
  const [imageSrc5, setImageSrc5]: any = useState<File>();

  const boardData: BoardData = {
    title: title,
    content: editorContent,
    imgUrls: [imageSrc1, imageSrc2, imageSrc3, imageSrc4, imageSrc5],
  };

  const handleCreateBoard = async () => {
    try {
      // 게시물 생성 뮤테이션 실행
      const createdBoard = await createBoardMutation.mutateAsync(boardData);
      console.log('게시물 생성 성공:', createdBoard);
    } catch (error) {
      console.error('게시물 생성 실패:', error);
    }
  };

  const onUpload = (
    e: React.ChangeEvent<HTMLInputElement>,
    setImgFiles: React.Dispatch<React.SetStateAction<File | null>>,
  ) => {
    const target = e.currentTarget;
    const files = (target.files as FileList)[0];

    if (!files) {
      setImgFiles(null);
      return;
    }

    const reader = new FileReader();
    reader.readAsDataURL(files);
    reader.onloadend = async () => {
      const uploadedImageUrl = await postImgHandler(files, 'boards');
      setImgFiles(uploadedImageUrl);
    };
  };

  return (
    <div className="container mx-auto mt-16 mb-20">
      <div>
        <input
          className="w-4/5 h-16 mt-20 mb-4 border border-black"
          placeholder="제목을 입력하세요"
          value={title}
          onChange={e => setTitle(e.target.value)}
        />
      </div>
      <div>
        <input
          type="file"
          accept="image/*"
          onChange={e => onUpload(e, setImageSrc1)}
        ></input>
        <img className="w-16" src={imageSrc1} />
        <input
          type="file"
          accept="image/*"
          onChange={e => onUpload(e, setImageSrc2)}
        ></input>
        <img className="w-16" src={imageSrc2} />
        <input
          type="file"
          accept="image/*"
          onChange={e => onUpload(e, setImageSrc3)}
        ></input>
        <img className="w-16" src={imageSrc3} />
        <input
          type="file"
          accept="image/*"
          onChange={e => onUpload(e, setImageSrc4)}
        ></input>
        <img className="w-16" src={imageSrc4} />
        <input
          type="file"
          accept="image/*"
          onChange={e => onUpload(e, setImageSrc5)}
        ></input>
        <img className="w-16" src={imageSrc5} />
      </div>
      <PeacockTextEditor />
      <button className="border" onClick={handleCreateBoard}>
        등록하기
      </button>
    </div>
  );
};

export default PeacockWrite;
