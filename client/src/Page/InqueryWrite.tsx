import TextEditor from '../Components/Care/TextEditor';
import Dropdown from '../Components/Inquery/Dropdown';

const InqueryWrite = () => {
  return (
    <>
      <section className="p-10">
        <div className="space-y-8">
          <div className="text-3xl font-semibold mb-2">문의하기</div>
          <div className="text-2xl">문의 유형을 골라주세요</div>
          <div className="mb-2">
            <Dropdown />
          </div>
          <div className="text-lg">제목</div>
          <input className="focus:outline-none border p-2 rounded w-full border- lg:w-[700px]" />
          <div className="text-lg mt-4">요청 사항 제목을 입력해 주세요.</div>
          <div className="mt-2 text-sm text-gray-600  w-full h-full lg:w-[700px] ">
            문의&요청 내용을 입력해주세요. 처리 및 답변에 최대 약 7일 소요될 수
            있습니다.
          </div>
          <div className="mt-4 w-full h-full lg:w-[700px] lg:h-[170px]">
            <TextEditor />
          </div>
        </div>
        <button className="bg-customGreen hover:bg-customHoverGreen text-gray-700 font-bold py-2 px-4 rounded transition duration-300 ease-in-out">
          Submit
        </button>
      </section>
    </>
  );
};

export default InqueryWrite;
