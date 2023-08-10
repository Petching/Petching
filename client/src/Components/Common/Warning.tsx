import { Dispatch, SetStateAction } from 'react';

interface WarningType {
  title: string;
  sub: string;
  fn: () => void;
  setState: Dispatch<SetStateAction<boolean>>;
}

const Warning: React.FC<WarningType> = ({ title, sub, fn, setState }) => {
  return (
    <div className="absolute top-0 left-0 w-full h-full bg-black bg-opacity-10 flex items-center justify-center">
      <div className="w-1/2 h-24 bg-white flex flex-col justify-center items-center rounded max-w-2xl min-w-fit">
        <h1 className="text-lg">{title}</h1>
        <p className="my-2 px-3">{sub}</p>
        <div>
          <button
            className="mr-3 hover:text-slate-400"
            onClick={() => setState(() => false)}
          >
            취소
          </button>
          <button className="hover:text-red-700" onClick={fn}>
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

export default Warning;
