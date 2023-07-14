//package com.Petching.petching.room;
//
//
//import com.Petching.petching.response.MultiResponse;
//import com.Petching.petching.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import javax.validation.Valid;
//import javax.validation.constraints.Positive;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Validated
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/rooms")
//public class RoomController {
//
//    private final RoomService roomService;
//    private final UserService userService;
//    private final RoomMapper mapper;
//
//    @Value("${default.thumbnail.image}")
//    private String thumbnail;
//
//    @PostMapping("/{member-id}/add")
//    public ResponseEntity postRoom(@PathVariable("member-id") long memberId,
//                                   @Valid @RequestBody RoomDto.Post requestBody
////                                   Authentication authentication
//    ) {
//
////        Map<String, Object> principal = (Map) authentication.getPrincipal();
////        long jwtMemberId = ((Number) principal.get("memberId")).longValue();
////
////        if(jwtMemberId != requestBody.getAdminMemberId() || jwtMemberId != memberId) {
////            ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.FORBIDDEN, "권한이 없는 사용자 입니다.");
////            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
////        }
//
//        requestBody.setAdminMemberId(memberId);
//
//        if(requestBody.getImageUrl() == null) {
//            requestBody.setImageUrl(thumbnail);
//        }
//
//        Room room = mapper.postDtoToRoom(requestBody);
//        room = roomService.createRoom(room, requestBody);
//
//        return new ResponseEntity<>(mapper.roomToPostResponseDto(room), HttpStatus.CREATED);
//    }
//
//
//    //방제 중복체크
//    @PostMapping("/check")
//    public ResponseEntity checkTitle(@Valid @RequestBody RoomDto.CheckTitle requestBody) {
//        ResponseEntity checkTitle = roomService.verifyExistsCheck(requestBody.getTitle());
//
//        if(checkTitle != null) return checkTitle;
//        return new ResponseEntity(requestBody.getTitle(), HttpStatus.OK);
//    }
//
//
//    @PatchMapping("/{room-id}/edit")
//    public ResponseEntity patchRoom(@PathVariable("room-id") @Positive long roomId,
//                                    @Valid @RequestBody RoomDto.Patch requestBody) {
////                                    Authentication authentication)
//
//
////        Map<String, Object> principal = (Map) authentication.getPrincipal();
////        long jwtMemberId = ((Number) principal.get("memberId")).longValue();
////        boolean isAdmin = (boolean) principal.get("isAdmin");
////
////        if (isAdmin == false) {
////            if (jwtMemberId != requestBody.getAdminMemberId()) {
////                ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.FORBIDDEN, "권한이 없는 사용자 입니다.");
////                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
////            }
////        }
//
//        requestBody.setRoomId(roomId);
//        //log.info("비밀번호 확인 1 {}",requestBody.getPassword());
//        Room room = mapper.patchDtoToRoom(requestBody);
//        //log.info("비밀번호 확인 2 {}", room.getPassword());
//        room = roomService.updateRoom(room, requestBody.getAdminMemberId());
//        //log.info("비밀번호 확인 3 {}", room.getPassword());
//        return new ResponseEntity<>(mapper.roomToPatchResponseDto(room),HttpStatus.OK);
//    }
//
//
//
//
//    @PatchMapping("/{room-id}/switch")
//    public ResponseEntity patchAdmin(@PathVariable("room-id") @Positive long roomId,
//                                     @Valid @RequestBody RoomDto.PatchAdmin requestBody) {
////                                     Authentication authentication) {
//
////        Map<String, Object> principal = (Map) authentication.getPrincipal();
////        long jwtMemberId = ((Number) principal.get("memberId")).longValue();
////        Room findRoom = roomService.findVerifiedRoom(roomId);
////        boolean isAdmin = (boolean) principal.get("isAdmin");
////
////        if (isAdmin == false) {
////            if (jwtMemberId != findRoom.getAdminMemberId()){
////                ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.FORBIDDEN, "권한이 없는 사용자 입니다.");
////                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
////            }
////        }
//        requestBody.setRoomId(roomId);
//        Room room = mapper.patchAdminDtoToRoom(requestBody);
//        room = roomService.switchAdmin(room, requestBody.getNewAdminId());
//        return new ResponseEntity<>(mapper.roomToPatchAdminResponseDto(room),HttpStatus.OK);
//    }
//
//
//
//
//    @PostMapping("/{room-id}/favorite")
//    public ResponseEntity postFavorite(@PathVariable("room-id") @Positive long roomId,
//                                       @Valid @RequestBody RoomDto.PostFavorite requestBody) {
////                                       Authentication authentication) {
//
////        if(authentication==null){
////            ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, "로그인이 필요한 서비스입니다.");
////            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
////        }
//        requestBody.setRoomId(roomId);
//        Room room = mapper.PostFavoriteDtoToRoom(requestBody);
//
//        if(requestBody.isFavorite()) {
//            roomService.addFavorite(room, requestBody.isFavorite(), requestBody.getMemberId());
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        roomService.undoFavorite(room, requestBody.isFavorite(), requestBody.getMemberId());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//
//
//
//    @GetMapping("/new")
//    public ResponseEntity getNewRooms(@RequestParam(value = "page", defaultValue = "1") @Positive int page,
//                                      @RequestParam(value = "size", defaultValue = "10") @Positive int size) {
//        Page<Room> roomPage = roomService.findNewRooms(page-1, size);
//        List<Room> roomList = roomPage.getContent();
//        List<RoomDto.GetNewRoomResponseDtos> responseDtosList = mapper.roomToNewRoomResponseDtos(roomList);
//        return new ResponseEntity<>(
//                new MultiResponse<>(responseDtosList, roomPage), HttpStatus.OK);
//    }
//
//
//
//    @GetMapping("/0/recommend") //url 수정
//    public ResponseEntity getRecommendRooms(@RequestParam(value = "page", defaultValue = "1") @Positive int page,
//                                            @RequestParam(value = "size", defaultValue = "10") @Positive int size) {
//
//        Page<Room> nonMemberPage = roomService.findRecommendRoomsNonMember(page - 1, size);
//        List<Room> nonMemberList = nonMemberPage.getContent();
//        List<RoomDto.GetRecommendRoomResponseDtos> responseDtoList = mapper.memberToNonMemberRecommendResponseDtos(nonMemberList);
//
//        return new ResponseEntity<>(
//                new MultiResponse<>(responseDtoList, nonMemberPage), HttpStatus.OK);
//    }
//
//
//    @GetMapping("/{user-id}/recommend") //url 수정
//    public ResponseEntity getRecommendRooms(@PathVariable("user-id") long userId,
//                                            @RequestParam(value = "page", defaultValue = "1") @Positive int page,
//                                            @RequestParam(value = "size", defaultValue = "10") @Positive int size) {
//
//        userService.verifiedUser(userId);
//        //log.info("# 회원 {}",member);
//        Page<Room> recommendPage = roomService.findRecommendRooms(page-1, size);
//
//        //null 일 경우 빈응답 페이지 반환
//        if (recommendPage == null || recommendPage.isEmpty()) {
//            return new ResponseEntity<>(
//                    new MultiResponse<>(new ArrayList<>(), Page.empty()),
//                    HttpStatus.OK
//            );
//        }
//
//        List<Room> recommendList = recommendPage.getContent();
//        List<RoomDto.GetRecommendRoomResponseDtos> responseDtoList = mapper.memberToRecommendResponseDtos(recommendList);
//        //log.info("# 응답리스트 {}",responseDtoList.isEmpty());
//
//        return new ResponseEntity<>(
//                new MultiResponse<>(responseDtoList,recommendPage), HttpStatus.OK);
//    }
//
//
//
//
//    @DeleteMapping("/{room-id}")
//    public ResponseEntity deleteRoom(@PathVariable("room-id") @Positive long roomId,
//                                     @RequestParam("user-id") @Positive long userId) {
////                                     Authentication authentication){
//
////        Map<String, Object> principal = (Map) authentication.getPrincipal();
////        long jwtMemberId = ((Number) principal.get("memberId")).longValue();
////        boolean isAdmin = (boolean) principal.get("isAdmin");
////        Room room = roomService.findRoom(roomId);
////        long adminId = room.getAdminMemberId();
////
////        if (isAdmin == false) {
////            if (jwtMemberId != memberId || jwtMemberId != adminId || memberId != adminId) {
////                ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.FORBIDDEN, "권한이 없는 사용자 입니다.");
////                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
////            }
////        }
//        roomService.deleteRoom(roomId); //완전삭제
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//}