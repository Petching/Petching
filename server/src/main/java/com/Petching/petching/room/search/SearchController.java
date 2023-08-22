package com.Petching.petching.room.search;

import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.room.dto.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;


    @GetMapping
    public ResponseEntity getSearchRoomTitle(@RequestParam(value = "sort") String sort, @RequestParam("keyword") String query,
                                             @RequestParam(value = "page", defaultValue = "1") @Positive int page,
                                             @RequestParam(value = "size", defaultValue = "10") @Positive int size) {

        Page<RoomDto.SearchResponseDto> searchPage = searchService.searchRoomTitles(page-1, size, sort, query);
        List<RoomDto.SearchResponseDto> searchList = searchPage.getContent();

        return new ResponseEntity<>(
                new MultiResponse<>(searchList,searchPage), HttpStatus.OK);
    }
}
