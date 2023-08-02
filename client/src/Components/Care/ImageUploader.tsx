import React, { useState } from 'react';

const ImageUploader = () => {
  const [image, setImage] = useState<File | null>(null);
  const [preview, setPreview] = useState<string | null>(null);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files && e.target.files[0];
    if (file) {
      setImage(file);
      setPreview(URL.createObjectURL(file));
    }
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // 이미지를 서버에 업로드하거나 처리하는 로직을 작성합니다.
    console.log('서버로 이미지 전송: ', image);
  };

  return (
    <div>
      {preview && <img src={preview} alt="preview" width="200" />}
      <form onSubmit={handleSubmit}>
        <input type="file" accept="image/*" onChange={handleImageChange} />
        <button type="submit">Upload</button>
      </form>
    </div>
  );
};

export default ImageUploader;
