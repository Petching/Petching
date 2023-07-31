const express = require("express");
const { Server } = require("socket.io");
const http = require("http");
const cors = require("cors");
const app = express();
app.use(cors());

const mongoose = require("mongoose");
const Message = require("./message");

const PORT = process.env.PORT || 4000;
const MONGODB_URI = "mongodb://localhost:27017/chatting";

mongoose.connect(MONGODB_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

const db = mongoose.connection;
db.on("error", console.error.bind(console, "connection error:"));
db.once("open", function () {
  console.log("Connected to MongoDB");
});

const server = http.createServer(app);
const io = new Server(server, {
  cors: {
    origin: "http://localhost:3000",
    methods: ["GET", "POST"],
  },
});

io.on("connection", (socket) => {
  console.log("New client connected");

  socket.on("joinRoom", async (room) => {
    console.log("socket - joinRoom");
    socket.join(room);
    socket.emit("notification", `You have joined room ${room}`);

    const messages = await Message.find({ room: room })
      .sort({ createdAt: -1 })
      .limit(50)
      .exec();

    socket.emit(
      "load_messages",
      messages.reverse().map((msg) => ({
        room: msg.room,
        message: msg.message,
        createdAt: msg.createdAt,
      }))
    );
  });

  socket.on("chat", async (data) => {
    console.log(data);
    io.to(data.room).emit("chat", data);
    const message = new Message({
      room: data.room,
      message: data.message,
    });
    message
      .save()
      .then((result) => console.log("Message saved:", result))
      .catch((err) => console.error("Error saving message:", err));
  });

  socket.on("disconnect", () => {
    console.log("Client disconnected");
  });
});

server.listen(PORT, () => console.log(`Server started on ${PORT}`));
