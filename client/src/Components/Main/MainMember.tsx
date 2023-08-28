/* eslint-disable */
import { useState } from 'react';
import { AiOutlineSwap } from 'react-icons/ai';
import Members from './Members';

const member_fe = [
  {
    icon: 'https://avatars.githubusercontent.com/u/92746200?v=4',
    name: '곽지현',
    github: 'https://github.com/938938',
    part: '메인, 헤더, 푸터, 마이페이지',
  },
  {
    icon: 'https://avatars.githubusercontent.com/u/82639552?v=4',
    name: '염도경',
    github: 'https://github.com/yeomdogyeong',
    part: '로그인, 회원가입, 채팅, 문의하기',
  },
  {
    icon: 'https://avatars.githubusercontent.com/u/120395149?v=4',
    name: '김성수',
    github: 'https://github.com/ggggggggithub',
    part: '자랑하기 게시판, 게시글, 댓글',
  },
  {
    icon: 'https://avatars.githubusercontent.com/u/82007474?v=4',
    name: '노호준',
    github: 'https://github.com/nowaveosu',
    part: '돌봄 리스트 게시판, 게시글, 태그',
  },
];
const member_be = [
  {
    icon: 'https://avatars.githubusercontent.com/u/88307264?v=4',
    name: '김은정',
    github: 'https://github.com/196code-gray',
    part: '보안, 회원 CRUD',
  },
  {
    icon: 'https://avatars.githubusercontent.com/u/120084774?v=4',
    name: '김상래',
    github: 'https://github.com/ksr0818',
    part: '돌봄페이지 CRUD, 태그, 채팅',
  },
  {
    icon: 'https://avatars.githubusercontent.com/u/120099321?v=4',
    name: '조만기',
    github: 'https://github.com/sniij',
    part: '자랑페이지 CRUD, 댓글 CRUD',
  },
];

const MainMember = () => {
  const [member, setMember] = useState(true);
  return (
    <div>
      <div className="hidden sm:flex w-full flex-col sm:flex-row sm:justify-evenly sm:py-20">
        <Members members={member_fe} type="FRONT END" />
        <Members members={member_be} type="BACK END" />
      </div>
      <div className="block sm:hidden relative py-10 px-5">
        <button
          className="flex justify-center items-center absolute top-5 right-5"
          onClick={() => setMember(prev => !prev)}
        >
          <AiOutlineSwap className="text-hoverGreen" />
          <p className="ml-1 text-hoverGreen">{member ? 'BACK' : 'FRONT'}</p>
        </button>
        {member ? (
          <Members members={member_fe} type="FRONT END" />
        ) : (
          <Members members={member_be} type="BACK END" />
        )}
      </div>
    </div>
  );
};

export default MainMember;
