import Header from './Header/Header';
import Contents from './Contents/Contents';
import { Outlet } from 'react-router-dom';
import Footer from './Footer/Footer';

const GeneraLayout = () => {
  return (
    <>
      <Header />
      <Contents>
        <Outlet />
      </Contents>
      <Footer />
    </>
  );
};

export default GeneraLayout;
