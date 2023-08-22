package com.Petching.petching.room.history;

import com.Petching.petching.room.search.SearchService;
import com.Petching.petching.room.search.SortMethod;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import com.Petching.petching.room.entity.Room;
import com.Petching.petching.room.entity.UserRoom;
import com.Petching.petching.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomHistoryService {

    private final RoomHistoryRepository roomHistoryRepository;
    private final UserService userService;
    private final RoomService roomService;
    private final SearchService searchService;

    public RoomHistoryDto checkVisitHistory(long userId, String roomTitle) {

        User user = userService.findUser(userId);
        Room room = roomService.findRoom(roomTitle);

        RoomHistory existingHistory = roomHistoryRepository.findByUser_UserIdAndRoom_Title(userId,roomTitle);
        if(existingHistory != null) roomHistoryRepository.delete(existingHistory);

        RoomHistory roomHistory = new RoomHistory();
        roomHistory.setUser(user);
        roomHistory.setRoom(room);
        roomHistory.setVisitTime(LocalDateTime.now());

        RoomHistoryDto roomHistoryDto = createRoomHistoryDto(roomHistory);
        roomHistoryRepository.save(roomHistory);

        return roomHistoryDto;
    }



    public Page<RoomHistoryDto> getVisitHistory(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        String sort = "newRoom";


        List<RoomHistory> roomHistoryList = roomHistoryRepository.findAll();
        List<RoomHistory> sortList = settingSort(roomHistoryList,sort);
        List<RoomHistoryDto> roomHistoryDtoList = sortList.stream()
                .map(this::createRoomHistoryDto)
                .collect(Collectors.toList());

        return new PageImpl<>(roomHistoryDtoList, pageable, roomHistoryList.size());
    }

    public List<RoomHistory> settingSort(List<RoomHistory> searchRooms, String sort) {
        if (sort != null && sort.equals("newRoom")) {
            return searchRooms.stream().sorted(SortMethod.sortByNewHistory())
                    .collect(Collectors.toList());
        }
        return searchRooms;
    }




    private RoomHistoryDto createRoomHistoryDto(RoomHistory roomHistory) {
        UserRoom memberRoom = new UserRoom();
        memberRoom.setRoom(roomHistory.getRoom());

        return RoomHistoryDto.builder()
                .roomId(roomHistory.getRoom().getRoomId())
                .title(roomHistory.getRoom().getTitle())
                .userCurrentCount(roomHistory.getRoom().getUserCurrentCount())
                .createdAt(LocalDateTime.now())
                .build();

    }
}
