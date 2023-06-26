import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import SignIn from "./Page/signIn";
import SignUp from "./Page/signUp";
import ChatList from "./Page/chatList";
import Chatting from "./Page/chatting";

import "./App.css";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/signin" element={<SignIn />}></Route>
          <Route path="/signup" element={<SignUp />}></Route>
          <Route path="/chatlist" element={<ChatList />}></Route>
          <Route path="/chatting" element={<Chatting />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
