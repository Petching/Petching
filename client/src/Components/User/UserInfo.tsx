import { useState } from 'react';

const UserInfo = () => {
  const [onEdit, setOnEdit] = useState(false);

  return (
    <div className="flex items-center w-9/12 my-10 relative">
      <div className="mr-6">
        {onEdit ? (
          <label className="w-32 h-32 rounded overflow-hidden border relative block cursor-pointer hover:border-4">
            <div className="absolute bottom-0 left-0 w-full h-1/2 bg-white text-center p-2 opacity-80">
              이미지 <br />
              변경
            </div>
            <img
              src="https://cdn.pixabay.com/photo/2023/06/14/10/02/pied-flycatcher-8062744_640.jpg"
              alt="변경된 이미지"
              className="w-full h-full"
            />
            <input
              id="attach-file"
              type="file"
              accept="image/*"
              className="hidden"
            />
          </label>
        ) : (
          <img
            src="https://cdn.pixabay.com/photo/2023/06/14/10/02/pied-flycatcher-8062744_640.jpg"
            alt="유저의 프로필 이미지"
            className="w-32 h-32 rounded"
          />
        )}
      </div>
      <div>
        {onEdit ? (
          <input placeholder="유저 이름" className="block" />
        ) : (
          <p>유저 이름</p>
        )}
        {onEdit ? <input placeholder="주소" className="block" /> : <p>주소</p>}
        {onEdit ? <p>이메일은 수정이 불가능합니다.</p> : <p>이메일</p>}
      </div>
      {/* {onEdit && <button className="mr-3 absolute bottom-0">회원 탈퇴</button>} */}
      <div className="absolute right-0 bottom-0">
        {onEdit ? (
          <>
            <button className="mr-3 text-slate-400 hover:text-red-700">
              회원 탈퇴
            </button>
            <button
              onClick={() => setOnEdit(false)}
              className="hover:text-[#5fb7a1]"
            >
              수정 완료
            </button>
          </>
        ) : (
          <button
            onClick={() => setOnEdit(true)}
            className="hover:text-[#5fb7a1]"
          >
            회원 정보 수정
          </button>
        )}
      </div>
    </div>
  );
};

export default UserInfo;
