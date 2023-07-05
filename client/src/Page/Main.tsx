import { SectionsContainer, Section } from 'react-fullpage';
import Info from '../Components/Main/Info';
import Explan from '../Components/Main/Explan';
import MainPeacock from '../Components/Main/MainPeacock';
import MainMember from '../Components/Main/MainMember';

const Main = () => {
  const options = {
    anchors: ['info', 'explan', 'peacock', 'member'],
    activeClass: 'active', // the class that is appended to the sections links
    arrowNavigation: true, // use arrow keys
    delay: 1000, // the scroll animation speed
    navigation: true, // use dots navigatio
    scrollBar: false, // use the browser default scrollbar
  };

  return (
    <>
      <SectionsContainer {...options}>
        <Section>
          <Info />
        </Section>
        <Section>
          <Explan />
        </Section>
        <Section>
          <MainPeacock />
        </Section>
        <Section>
          <MainMember />
        </Section>
      </SectionsContainer>
    </>
  );
};

export default Main;
