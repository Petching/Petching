import { ChangeEvent, useState } from 'react';
import TextEditor from '../Components/Care/TextEditor';
import Dropdown from '../Components/Inquery/Dropdown';

const InqueryWrite = () => {
  const handleButtonClick = (e?: React.MouseEvent<HTMLButtonElement>) => {
    e?.preventDefault();
    alert('1대1 문의는 준비중입니다.');
  };
  const [title, setTitle] = useState<string>('');

  const handleTitle = (e: ChangeEvent<HTMLInputElement>) => {
    setTitle(e.target.value);
  };

  return (
    <>
      <section className="p-10 flex justify-center items-start  md:flex-col sm:flex-col">
        <div className="space-y-8">
          <div className="text-3xl font-semibold mb-2">문의하기</div>
          <div className="text-2xl">문의 유형을 골라주세요</div>
          <div className="mb-2">
            <Dropdown />
          </div>
          <div className="text-lg">제목</div>
          <input
            className="focus:outline-none border p-2 rounded w-full border- lg:w-[700px]"
            value={title}
            onChange={handleTitle}
          />
          <div className="text-lg mt-4">요청 사항 제목을 입력해 주세요.</div>
          <div className="mt-2 text-sm text-gray-600  w-full h-full lg:w-[700px] ">
            문의&요청 내용을 입력해주세요. 처리 및 답변에 최대 약 7일 소요될 수
            있습니다.
          </div>
          <div className="mt-4 w-full h-full lg:w-[700px] lg:h-[170px]">
            <TextEditor
              title={title}
              onClickButton={e => handleButtonClick(e)}
            />
          </div>
        </div>
      </section>
    </>
  );
};

export default InqueryWrite;
