import { PeacockComponent, Square } from '../Components/peacockComponent';

const Peacock = () => {
  return (
    <div className="flex flex-col items-center">
      <Square />
      <div className="flex flex-wrap justify-center">
        <PeacockComponent />
        <PeacockComponent />
        <PeacockComponent />
        <PeacockComponent />
        <PeacockComponent />
        <PeacockComponent />
      </div>
    </div>
  );
};

export default Peacock;
