import kakaoLogo from "../Style/kakaoLogo.png";

const ChatComponent: React.FC = () => {
  return (
    <div className="bg-white mx-auto flex items-center w-full h-[15vh] sm:w-[600px] sm:h-[150px] md:w-[800px] md:h-[140px] p-1 border border-#e0e0e0 rounded-lg">
      <img src={kakaoLogo} alt="kakao Image" className="h-20 w-20 ml-1" />
      <div className="font-semibold ml-2">mango</div>
      <div className=" ml-10">안녕하세요</div>
    </div>
  );
};

export default ChatComponent;
