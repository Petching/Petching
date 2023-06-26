import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import SignIn from "./Page/signIn";
import SignUp from "./Page/signUp";
import ChatList from "./Page/chatList";
import Chatting from "./Page/chatting";
import logo from "./logo.svg";
import "./App.css";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          <BrowserRouter>
            <Routes>
              <Route path="/signin" element={<SignIn />}></Route>
              <Route path="/signup" element={<SignUp />}></Route>
              <Route path="/chatlist" element={<ChatList />}></Route>
              <Route path="/chatting" element={<Chatting />}></Route>
            </Routes>
          </BrowserRouter>
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
