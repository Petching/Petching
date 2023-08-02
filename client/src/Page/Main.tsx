import { SectionsContainer, Section } from 'react-fullpage';
import Info from '../Components/Main/Info';
import Explain from '../Components/Main/Explain';
import MainPeacock from '../Components/Main/MainPeacock';
import MainMember from '../Components/Main/MainMember';

const Main = () => {
  const options = {
    anchors: ['info', 'explain', 'peacock', 'member'],
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
          <Explain />
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
