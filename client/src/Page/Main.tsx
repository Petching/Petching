import { SectionsContainer, Section } from 'react-fullpage';

const Main = () => {
  const options = {
    anchors: ['main', 'info', 'peacock', 'member'],
  };

  return (
    <SectionsContainer {...options}>
      <Section>Page 1</Section>
      <Section>Page 2</Section>
      <Section>Page 3</Section>
      <Section>Page 4</Section>
    </SectionsContainer>
  );
};

export default Main;
