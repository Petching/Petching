import { UserPostType } from '../../Util/types';

interface PageBtnsProps {
  data: UserPostType;
  page: number;
  setPage: (number: number) => void;
}

const PageBtns: React.FC<PageBtnsProps> = ({ data, page, setPage }) => {
  const pageNumber = Array(data?.pageInfo.totalPages)
    .fill(0)
    .map((_, index) => index + 1);

  return (
    <div className="mt-10 mb-20 flex justify-center">
      {pageNumber.length !== 0 &&
        pageNumber.map(number => (
          <button
            key={number}
            className={`w-5 h-10 mx-1 rounded ${
              number === page && 'text-hoverGreen'
            } hover:text-hoverGreen`}
            onClick={() => setPage(number)}
          >
            {number}
          </button>
        ))}
    </div>
  );
};

export default PageBtns;
