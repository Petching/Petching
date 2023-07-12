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

io.on("connection", (socket) => {
  console.log("New client connected");

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
