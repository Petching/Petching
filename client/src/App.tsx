import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import SignIn from "./Page/signIn";
import SignUp from "./Page/signUp";
import ChatList from "./Page/chatList";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/signup" element={<SignUp />}></Route>
        <Route path="/signin" element={<SignIn />}></Route>
        <Route path="/chatlist" element={<ChatList />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
