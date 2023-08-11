/* eslint-disable */
import { AiFillGithub } from 'react-icons/ai';

const MainMember = () => {
  const member_fe = [
    {
      icon: 'https://cdn.pixabay.com/photo/2023/05/09/07/18/space-7980556_1280.jpg',
      name: '곽지현',
      github: 'https://github.com/938938',
      part: '메인, 헤더, 푸터, 마이페이지',
    },
    {
      icon: 'https://cdn.pixabay.com/photo/2023/05/09/07/18/space-7980556_1280.jpg',
      name: '염도경',
      github: 'https://github.com/yeomdogyeong',
      part: '로그인, 회원가입, 무한스크롤, 채팅',
    },
    {
      icon: 'https://cdn.pixabay.com/photo/2023/05/09/07/18/space-7980556_1280.jpg',
      name: '김성수',
      github: 'https://github.com/ggggggggithub',
      part: '자랑하기 게시판, 게시글, 댓글, 좋아요',
    },
    {
      icon: 'https://cdn.pixabay.com/photo/2023/05/09/07/18/space-7980556_1280.jpg',
      name: '노호준',
      github: 'https://github.com/nowaveosu',
      part: '돌봄 리스트 게시판, 게시글, 찜(즐겨찾기) (태그, 필터링, 정렬, 검색)',
    },
  ];
  const member_be = [
    {
      icon: 'https://cdn.pixabay.com/photo/2023/05/09/07/18/space-7980556_1280.jpg',
      name: '김은정',
      github: 'https://github.com/196code-gray',
      part: '보안, 회원 CRUD',
    },
    {
      icon: 'https://cdn.pixabay.com/photo/2023/05/09/07/18/space-7980556_1280.jpg',
      name: '김상래',
      github: 'https://github.com/ksr0818',
      part: '돌봄페이지 CRUD, 태그, 채팅',
    },
    {
      icon: 'https://cdn.pixabay.com/photo/2023/05/09/07/18/space-7980556_1280.jpg',
      name: '조만기',
      github: 'https://github.com/sniij',
      part: '자랑페이지 CRUD, 댓글 CRUD',
    },
  ];

  return (
    <div className="flex w-full justify-evenly items-center py-20">
      <div>
        <p>FRONT END</p>
        {member_fe.map((data, index) => (
          <div key={index} className="flex my-3">
            <div className="w-32 h-32 overflow-hidden rounded">
              <img
                src={data.icon}
                alt="멤버 이미지"
                className="w-full h-full"
              />
            </div>
            <div className="flex items-center">
              <div className="pl-10">
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
      <div>
        <p>BACK END</p>
        {member_be.map((data, index) => (
          <div key={index} className="flex my-3">
            <div className="w-32 h-32 overflow-hidden rounded">
              <img
                src={data.icon}
                alt="멤버 이미지"
                className="w-full h-full"
              />
            </div>
            <div className="flex items-center">
              <div className="pl-10">
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
    </div>
  );
};

export default MainMember;
