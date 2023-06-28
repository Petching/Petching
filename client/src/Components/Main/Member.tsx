const Member = () => {
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
      name: '이난영',
      github: 'https://github.com/NYinJP',
      part: '자랑페이지 CRUD, 댓글 CRUD',
    },
  ];
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
      part: '로그인, 회원가입, 무한스크롤',
    },
    {
      icon: 'https://cdn.pixabay.com/photo/2023/05/09/07/18/space-7980556_1280.jpg',
      name: '이현진',
      github: 'https://github.com/lhj5924',
      part: '돌봄페이지, 자랑페이지',
    },
  ];
  return (
    <div className="flex w-full h-full justify-evenly items-center">
      <div>
        <p>BACK END</p>
        {member_be.map((data, index) => (
          <div key={index} className="flex my-3">
            <div className="w-40 h-40 overflow-hidden rounded">
              <img
                src={data.icon}
                alt="멤버 이미지"
                className="w-full h-full"
              />
            </div>
            <div className="flex items-center">
              <div className="pl-10">
                <p>이름 : {data.name}</p>
                <p className="my-2">깃허브 : {data.github}</p>
                <p>담당 : {data.part}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
      <div>
        <p>FRONT END</p>
        {member_be.map((data, index) => (
          <div key={index} className="flex my-3">
            <div className="w-40 h-40 overflow-hidden rounded">
              <img
                src={data.icon}
                alt="멤버 이미지"
                className="w-full h-full"
              />
            </div>
            <div className="flex items-center">
              <div className="pl-10">
                <p>이름 : {data.name}</p>
                <p className="my-2">깃허브 : {data.github}</p>
                <p>담당 : {data.part}</p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Member;
