package com.Petching.petching.room.search;

import com.Petching.petching.room.dto.RoomDto;
import com.Petching.petching.room.entity.Room;
import com.Petching.petching.room.entity.UserRoom;
import com.Petching.petching.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final RoomRepository roomRepository;

    public Page<RoomDto.SearchResponseDto> searchRoomTitles(int page, int size, String sort, String query) {
        Pageable pageable = PageRequest.of(page, size);
        List<Room> searchRooms = roomRepository.findAllByTitleContaining(query, pageable);
        List<Room> sortedRooms = settingSort(searchRooms, sort); // 정렬된 Room 리스트

        List<RoomDto.SearchResponseDto> searchResponseList = sortedRooms.stream()
                .map(this::createSearchResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(searchResponseList, pageable, searchRooms.size());
    }



    public RoomDto.SearchResponseDto createSearchResponseDto(Room room) {
        UserRoom userRoom = new UserRoom();
        userRoom.setRoom(room);

        return RoomDto.SearchResponseDto.builder()
                .roomId(room.getRoomId())
                .title(room.getTitle())
                .userCurrentCount(room.getUserCurrentCount())
                .createdAt(LocalDateTime.now())
                .build();
    }



    public List<Room> settingSort(List<Room> searchRooms, String sort) {

        if (sort != null && sort.equals("oldRoom")) {
            return searchRooms.stream().sorted(SortMethod.sortByOld())
                    .collect(Collectors.toList());

        } else if (sort != null && sort.equals("newRoom")) {
            return searchRooms.stream().sorted(SortMethod.sortByNew())
                    .collect(Collectors.toList());
        }
        return searchRooms;
    }
}

