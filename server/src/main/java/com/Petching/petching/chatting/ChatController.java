//package com.Petching.petching.chatting;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
////@Produces(MediaType.APPLICATION_JSON_VALUE)
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/chat")
//public class ChatController {
//    private final ChatService chatService;
//
//    @PostMapping
//    public ResponseEntity createRoom(@RequestBody String name) {
//        ChatRoom response = chatService.createRoom(name);
//        return new ResponseEntity<>(
//                new SingleResponse<>(response),
//                HttpStatus.OK);
//    }
//
//    @GetMapping
//    public List<ChatRoom> findAllRoom() {
//        return chatService.findAllRoom();
//    }
//}
