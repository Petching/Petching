//package com.Petching.petching.chat.controller;
//
//import com.Petching.petching.chat.dto.ChatRoomDto;
//import com.Petching.petching.chat.entity.ChatRoom;
//import com.Petching.petching.chat.service.ChatRoomService;
//import com.Petching.petching.login.oauth.userInfo.JwtToken;
//import com.Petching.petching.user.entity.User;
//import com.Petching.petching.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/chat")
//public class ChatRoomController {
//    private final ChatRoomService chatService;
//    private final JwtToken jwtToken;
//    private final UserService userService;
//
//    // 채팅 리스트 화면
//    @GetMapping("/room")
//    public String rooms(Model model) {
//        return "/chat/room";
//    }
//    // 모든 채팅방 목록 반환
//    @GetMapping("/rooms")
//    @ResponseBody
//    public List<ChatRoom> room() {
//        return chatService.findAllRoom();
//    }
//
//    // 채팅방 생성
//    @PostMapping("/room")
//    @ResponseBody
//    public ChatRoom createRoom(@RequestParam String name,
//                               @RequestHeader("Authorization") String authorization) {
//
//        authorization = authorization.replaceAll("Bearer ","");
//        User user = userService.findUser(jwtToken.extractUserIdFromToken(authorization));
//
//        return chatService.createRoom(name);
//    }
//
//    // 채팅방 입장 화면
////    @GetMapping("/room/enter/{roomId}")
////    public ChatRoom roomDetail(@Validated @RequestBody ChatRoomDto requestBody,
////                               @RequestHeader("Authorization") String authorization) {
////        authorization = authorization.replaceAll("Bearer ","");
////        User user = userService.findUser(jwtToken.extractUserIdFromToken(authorization));
////
////
////
////
////
////    }
////    public String roomDetail(Model model, @PathVariable String roomId) {
////        model.addAttribute("roomId", roomId);
////        return "/chat/roomdetail";
////    }
//
//    // 특정 채팅방 조회
//    @GetMapping("/room/{roomId}")
//    @ResponseBody
//    public ChatRoom roomInfo(@PathVariable Long roomId) {
//
//        return chatService.findById(roomId);
//    }
//}
