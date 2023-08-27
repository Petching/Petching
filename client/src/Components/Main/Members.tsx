import { AiFillGithub } from 'react-icons/ai';

interface MemberType {
  icon: string;
  name: string;
  github: string;
  part: string;
}

interface MembersProps {
  members: MemberType[];
  type: 'FRONT END' | 'BACK END';
}

const Members: React.FC<MembersProps> = ({ members, type }) => {
  return (
    <div className="w-full sm:w-1/3 text-sm sm:text-base">
      <p className="text-lg sm:text-xl">{type}</p>
      {members.map((data, index) => (
        <div
          key={index}
          className="flex my-3 border border-white p-2 hover:border-customBlue rounded-full"
        >
          <div className="w-16 h-16 sm:w-28 sm:h-28 overflow-hidden rounded-full">
            <img src={data.icon} alt="멤버 이미지" className="w-full h-full" />
          </div>
          <div className="flex items-center">
            <div className="pl-5 sm:pl-10">
              <p>이름 : {data.name}</p>
              <p className="my-2 flex items-center">
                깃허브 :
                <button
                  className="ml-2"
                  onClick={() => {
                    window.open(data.github, '_blank');
                  }}
                >
                  <AiFillGithub />
                </button>
              </p>
              <p>담당 : {data.part}</p>
            </div>
          </div>
        </div>
      ))}
    </div>
  );
};

export default Members;
