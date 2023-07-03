const KakaoLogin = () => {
  const restApiKey = process.env.REACT_APP_REST_API_KEY;
  const redirectUrl = process.env.REACT_APP_REDIRECT_URL;

  const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${restApiKey}&redirect_uri=${redirectUrl}&response_type=code;`;

  const loginHandler = () => {
    window.location.href = kakaoAuthUrl;
  };

  return <div onClick={loginHandler}>카카오톡 로그인</div>;
};

export default KakaoLogin;
