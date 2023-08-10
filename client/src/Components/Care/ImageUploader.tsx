import React, { useState } from 'react';

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

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // 이미지를 서버에 업로드하거나 처리하는 로직을 작성합니다.
    console.log('서버로 이미지 전송: ', imageList);

    // 업로드 완료 콜백에 URL 배열 전달
    setTimeout(() => {
      onUploadComplete(previews);
    }, 1000);
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
