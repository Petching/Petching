package com.Petching.petching.carepost.controller;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.carepost.mapper.CarePostMapper;
import com.Petching.petching.carepost.repository.CarePostRepository;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.response.PageInfo;
import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.carepost.service.CarePostService;
import com.Petching.petching.utils.URICreator;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@Validated
@RequestMapping("/careposts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CarePostController {

    private final CarePostService service;
    private final CarePostMapper mapper;

    @PostMapping
    public ResponseEntity postCarePost(@Validated @RequestBody CarePostDto.Post requestBody) {

        CarePost createdPost = service.savePost(requestBody);

        URI uri = URICreator.createUri("/post", createdPost.getPostId());

        return ResponseEntity.created(uri).build();
    }

    @Transactional
    @PatchMapping("/{post-id}")
    public ResponseEntity patchCarePost(@PathVariable("post-id") @Positive long postId,
                                        @Validated @RequestBody CarePostDto.Patch requestBody) {
//        CarePost patch = mapper.carePostPatchDtoToCarePost(requestBody);

        CarePost updatedPost = service.updatePost(requestBody,postId);

        return new ResponseEntity(new SingleResponse<>(mapper.carePostToCarePostResponseDto(updatedPost)),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllCarePost(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CarePost> postPage = service.findAllPost(pageable);

        List<CarePostDto.Response> response = postPage
                .stream()
                .map(post->mapper.carePostToCarePostResponseDto(post))
                .collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo(
                postPage.getNumber(),
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages()
        );
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Info", new Gson().toJson(pageInfo));

        return ResponseEntity.ok().headers(headers).body(new MultiResponse<>(response,postPage));

    }

    @GetMapping("/{post-id}")
    public ResponseEntity getCarePost(@PathVariable("post-id") @Positive long postId) {
        CarePost find = service.findPost(postId);

        return new ResponseEntity(new SingleResponse<>(mapper.carePostToCarePostResponseDto(find)),HttpStatus.OK);
    }

    @DeleteMapping("/{post-id}/{member-id}")
    public ResponseEntity deleteCarePost(@PathVariable("post-id") @Positive long postId,
                                         @PathVariable("member-id") @Positive long userId) {
        service.deletePost(postId,userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
