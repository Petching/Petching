const Google = () => {
  const restApiKey =
    '73964203045-8phlgt4m3rgdjktnja713ebmmkmm45fk.apps.googleusercontent.com';
  const redirectUrl = 'http://localhost:3000/googlelogin';
  const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${restApiKey}&redirect_uri=${redirectUrl}&response_type=code`;

  const loginHandler = () => {
    window.location.href = kakaoAuthUrl;
  };

  return <div onClick={loginHandler}>구글 로그인</div>;
};

export default Google;
