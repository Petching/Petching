const express = require("express");
const { Server } = require("socket.io");
const http = require("http");
const cors = require("cors");
const app = express();
app.use(cors());

const PORT = process.env.PORT || 4000;

const server = http.createServer(app);
const io = new Server(server, {
  cors: {
    origin: "http://localhost:3000",
    methods: ["GET", "POST"],
  },
});

const rooms = {};

io.on("connection", (socket) => {
  console.log("New client connected");

  socket.on("createRoom", (room) => {
    // 채팅방이 이미 존재하는 경우
    if (rooms[room]) {
      socket.emit("notification", `Room ${room} already exists`);
    } else {
      // 새로운 채팅방을 생성하고, 사용자를 참여시킴
      rooms[room] = true;
      socket.join(room);
      socket.emit("notification", `Room ${room} created and joined`);
    }
  });

  socket.on("joinRoom", (room) => {
    socket.join(room);
    socket.emit("notification", `You have joined room ${room}`);
  });

  socket.on("chat", (data) => {
    io.to(data.room).emit("chat", data);
  });

  socket.on("disconnect", () => {
    console.log("Client disconnected");
  });
});

server.listen(PORT, () => console.log(`Server started on ${PORT}`));
