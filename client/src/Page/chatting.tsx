import ChattingComponent from '../Components/Chatting/chattingComponent';
const Chatting = () => {
  //roomId 부분을 채팅방의 고유 id를 정하는 방법을 정하고 그에 따르 구현방법을 모색해야한다.
  //1. 사용자 기반 - 닉네임1닉네임2
  //2. 무작위의 임의의 문자열
  //3. url의 endpoint로 만들기? -> useParams사용

  return (
    <div className="h-screen flex items-center justify-center bg-gray-100">
      <ChattingComponent />
    </div>
  );
};

export default Chatting;
