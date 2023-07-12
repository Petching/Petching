const Google = () => {
  const googleAuthUrl =
    'https://accounts.google.com/o/oauth2/auth?' +
    'client_id={73964203045-8phlgt4m3rgdjktnja713ebmmkmm45fk.apps.googleusercontent.com}&' +
    'redirect_uri={http://localhost:3000}&' +
    'response_type=token&' +
    'scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile';
  const loginHandler = () => {
    window.location.href = googleAuthUrl;
  };

  return <div onClick={loginHandler}>구글 로그인</div>;
};

export default Google;
