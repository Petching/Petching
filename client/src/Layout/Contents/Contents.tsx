type ContentsProps = {
  children: React.ReactNode;
};

const Contents: React.FC<ContentsProps> = ({ children }) => {
  return <div className="mt-14">{children}</div>;
};

export default Contents;
