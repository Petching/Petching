type ContentsProps = {
  children: React.ReactNode;
};

const Contents: React.FC<ContentsProps> = ({ children }) => {
  return <div className="mt-[4.5rem]">{children}</div>;
};

export default Contents;
