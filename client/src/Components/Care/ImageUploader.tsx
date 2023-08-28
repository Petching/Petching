import React, { useState } from 'react';

interface ImageUploaderProps {
  onUploadComplete: (uploadedUrls: File[]) => void;
}

const ImageUploader: React.FC<ImageUploaderProps> = ({ onUploadComplete }) => {
  const [previews, setPreviews] = useState<string[]>([]);

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    if (files) {
      const fileList = Array.from(files);
      setPreviews(fileList.map(file => URL.createObjectURL(file)));
      onUploadComplete(fileList);
    }
  };

  return (
    <div>
      {previews.map((preview, index) => (
        <img key={index} src={preview} alt={`preview-${index}`} width="200" />
      ))}

      <input
        type="file"
        accept="image/*"
        multiple
        onChange={handleImageChange}
      />
    </div>
  );
};

export default ImageUploader;
