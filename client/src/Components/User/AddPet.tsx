import { Dispatch, SetStateAction } from 'react';

type Props = {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
};
const AddPet: React.FC<Props> = ({ open, setOpen }) => {
  return (
    <form
      className={`border rounded w-[400px] p-5 flex flex-col justify-center items-center fixed top-1/2 right-1/2 translate-x-1/2 -translate-y-1/2 bg-white ${
        open ? 'block' : 'hidden'
      }`}
    >
      <label className="w-32 h-32 rounded overflow-hidden border relative block cursor-pointer hover:border-4">
        <div className="absolute bottom-0 left-0 w-full h-1/2 bg-white text-center p-2 opacity-80">
          이미지 <br />
          입력
        </div>
        <img
          src="https://cdn.pixabay.com/photo/2017/07/25/01/22/cat-2536662_1280.jpg"
          alt="입력된 이미지"
          className="w-full h-full"
        />
        <input
          id="attach-file"
          type="file"
          accept="image/*"
          className="hidden"
        />
      </label>
      <div className="flex flex-col my-5">
        <label className="flex justify-between w-[280px]">
          <p>이름</p>
          <input className="border" />
        </label>
        <label className="flex justify-between">
          <p>종(세부종)</p>
          <input className="border" />
        </label>
        <label className="flex justify-between">
          <p>성별</p>
          <input className="border" />
        </label>
        <label className="flex justify-between">
          <p>나이</p>
          <input className="border" />
        </label>
        <label className="flex justify-between">
          <p>몸무게</p>
          <input className="border" />
        </label>
        <label className="flex justify-between">
          <p>예방접종 유무</p>
          <input className="border" />
        </label>
        <label className="flex justify-between">
          <p>특이사항</p>
          <input className="border" />
        </label>
      </div>
      <div>
        <button className="mr-5" onClick={() => setOpen(false)} type="button">
          취소
        </button>
        <button type="submit">입력</button>
      </div>
    </form>
  );
};

export default AddPet;
