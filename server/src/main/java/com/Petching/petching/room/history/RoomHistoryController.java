package com.Petching.petching.room.history;

import com.Petching.petching.global.response.ErrorResponse;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.room.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class RoomHistoryController {

    private final RoomHistoryService roomHistoryService;
    private final RoomMapper mapper;

    @PostMapping("{member-id}/visit")
    public ResponseEntity visitRoom(@PathVariable("member-id") long memberId,
                                    @RequestParam("title") String title) {
        RoomHistoryDto roomHistory = roomHistoryService.checkVisitHistory(memberId,title);
        return ResponseEntity.status(HttpStatus.OK).body(roomHistory);
    }


    @GetMapping("/members/{member-id}/history")
    public ResponseEntity getUserRoomHistories(@PathVariable("member-id") Long memberId,
                                               @RequestParam(value = "page", defaultValue = "1") @Positive int page,
                                               @RequestParam(value = "size", defaultValue = "10") @Positive int size,
                                               Authentication authentication) {

        Map<String, Object> principal = (Map<String, Object>) authentication.getPrincipal();
        long jwtMemberId = ((Number) principal.get("memberId")).longValue();

        if(jwtMemberId != (memberId)) {
            ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.FORBIDDEN, "권한이 없는 사용자 입니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }

        Page<RoomHistoryDto> roomHistoryPage = roomHistoryService.getVisitHistory(page-1, size);
        List<RoomHistoryDto> roomHistoryList = roomHistoryPage.getContent();

        return new ResponseEntity<>(
                new MultiResponse<>(roomHistoryList, roomHistoryPage), HttpStatus.OK);
    }
}
