import React, { useState, useEffect } from 'react';
import axios from 'axios';

interface Post {
  boardId: number;
  title: string;
}

const Board = () => {
  const [posts, setPosts] = useState<Post[]>([]);

  useEffect(() => {
    const fetchPosts = async () => {
      const response = await axios.get(
        'https://server.petching.net/boards?page=1&size=10',
      );
      setPosts(response.data.data);
    };
    fetchPosts();
  }, []);

  return (
    <div>
      <h1>게시판</h1>
      <ul>
        {posts.map(post => (
          <li key={post.boardId}>{post.title}</li>
        ))}
      </ul>
    </div>
  );
};

export default Board;
