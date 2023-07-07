import { createBrowserRouter } from 'react-router-dom';
import { Router as RemixRouter } from '@remix-run/router/dist/router';
import GeneralLayout from './Layout/GeneraLayout';
import Main from './Page/Main';
import SignUp from './Page/signUp';
import SignIn from './Page/signIn';
import User from './Page/User';
import ChatList from './Page/chatList';
import Chatting from './Page/chatting';
import Kakao from './Components/Login/Kakao';
import Google from './Components/Login/Google';

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
        path: '/kakao',
        element: <Kakao />,
      },
      {
        path: '/google',
        element: <Google />,
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
    ],
  },
]);
