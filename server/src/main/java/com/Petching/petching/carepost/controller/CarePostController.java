package com.Petching.petching.carepost.controller;

import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.carepost.mapper.CarePostMapper;
import com.Petching.petching.carepost.repository.CarePostRepository;
import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.carepost.service.CarePostService;
import com.Petching.petching.utils.URICreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/carepost")
@RequiredArgsConstructor
public class CarePostController {

    private final CarePostService service;
    private final CarePostMapper mapper;
    private final CarePostRepository repository;

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
    public ResponseEntity getAllCarePost() {

        List<CarePost> posts = service.findAllPost();

        return new ResponseEntity<>(new SingleResponse<>(mapper.carePostsToCarePostResponseDtos(posts)), HttpStatus.OK);
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
