import React from 'react';

const Google = () => {
  const googleAuthUrl =
    'https://accounts.google.com/o/oauth2/auth?' +
    'client_id={111496604370-11ht3vfphnj2ut653h21g5ghu23gsu1p.apps.googleusercontent.com}&' +
    'redirect_uri={https://server.petching.net/login/oauth2/code/google}&' +
    'response_type=token&' +
    'scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile';

  return <div>구글 로그인</div>;
};

export default Google;
