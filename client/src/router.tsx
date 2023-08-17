/* eslint-disable */
import { createBrowserRouter } from 'react-router-dom';
import { Router as RemixRouter } from '@remix-run/router/dist/router';
import GeneralLayout from './Layout/GeneraLayout';
import Main from './Page/Main';
import SignUp from './Page/signUp';
import SignIn from './Page/signIn';
import User from './Page/User';
import ChatList from './Page/chatList';
import Chatting from './Page/chatting';
import KakaoLogin from './Components/Login/KakaoLogin';
import Peacock from './Page/Peacock';
// import PeacockDetail from './Page/peacockDetail';
import PeacockWrite from './Page/peacockWrite';
import GoogleLogin from './Components/Login/GoogleLogin';
import CareList from './Page/CareList';
import CareListPost from './Page/CareListPost';
import CareListAsk from './Page/CareListAsk';
import Inquery from './Page/Inquery';
import InqueryPopular from './Page/InqueryPopular';
import InqueryFind from './Page/InqueryFind';
import InqueryMap from './Page/InqueryMap';
export const routers: RemixRouter = createBrowserRouter([
  {
    path: '/',
    element: <GeneralLayout />,
    children: [
      {
        index: true,
        element: <Main />,
      },
      {
        path: '/inquery',
        element: <Inquery />,
      },
      {
        path: '/inquery/popular',
        element: <InqueryPopular />,
      },
      {
        path: '/inquery/find',
        element: <InqueryFind />,
      },
      {
        path: '/inquery/map',
        element: <InqueryMap />,
      },
      {
        path: '/kakao',
        element: <KakaoLogin />,
      },
      {
        path: '/google',
        element: <GoogleLogin />,
      },
      {
        path: '/signup',
        element: <SignUp />,
      },
      {
        path: '/signin',
        element: <SignIn />,
      },
      {
        path: '/user/:userId',
        element: <User />,
      },
      {
        path: '/chatlist',
        element: <ChatList />,
      },
      {
        path: '/chatting',
        element: <Chatting />,
      },
      {
        path: '/peacock',
        element: <Peacock />,
      },
      {
        path: '/peacock/write',
        element: <PeacockWrite />,
      },
      {
        path: '/carelist',
        element: <CareList />,
      },
      {
        path: '/carelistpost',
        element: <CareListPost />,
      },
      {
        path: '/carelistask',
        element: <CareListAsk />,
      },
    ],
  },
]);
