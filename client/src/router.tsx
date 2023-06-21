import { createBrowserRouter } from 'react-router-dom';
import { Router as RemixRouter } from '@remix-run/router/dist/router';
import GeneralLayout from './Layout/GeneraLayout';
import Main from './Page/Main';
import SignUp from './Page/signUp';
import SignIn from './Page/signIn';

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
        path: '/signup',
        element: <SignUp />,
      },
      {
        path: '/signin',
        element: <SignIn />,
      },
      {
        path: '/user/:userId',
        // element: <User />,
      },
    ],
  },
]);
