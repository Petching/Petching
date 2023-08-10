import React, { useState } from 'react';
import { postImgHandler } from '../../Util/postImg';

interface ImageUploaderProps {
  onUploadComplete: (uploadedUrls: string[]) => void;
}

const ImageUploader: React.FC<ImageUploaderProps> = ({ onUploadComplete }) => {
  const [imageList, setImageList] = useState<File[]>([]);
  const [previews, setPreviews] = useState<string[]>([]);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    if (files) {
      const fileList = Array.from(files);
      setImageList(fileList);
      setPreviews(fileList.map(file => URL.createObjectURL(file)));
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    console.log('서버로 이미지 전송: ', imageList);

    try {
      const imgUrls = [];
      // 순서대로 이미지 업로드 후 배열에 저장
      for (const img of imageList) {
        const imgUrl = await postImgHandler(img, 'careposts');
        imgUrls.push(imgUrl);
      }
      onUploadComplete(imgUrls);
    } catch (error) {
      console.error('이미지 업로드 실패', error);
    }
  };

  return (
    <div>
      {previews.map((preview, index) => (
        <img key={index} src={preview} alt={`preview-${index}`} width="200" />
      ))}
      <form onSubmit={handleSubmit}>
        <input
          type="file"
          accept="image/*"
          multiple
          onChange={handleImageChange}
        />
        <button type="submit">Upload</button>
      </form>
    </div>
  );
};

export default ImageUploader;
