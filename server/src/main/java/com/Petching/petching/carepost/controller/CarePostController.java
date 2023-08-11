package com.Petching.petching.carepost.controller;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.carepost.mapper.CarePostMapper;
import com.Petching.petching.carepost.repository.CarePostRepository;
import com.Petching.petching.login.oauth.userInfo.JwtToken;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.response.PageInfo;
import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.carepost.service.CarePostService;
import com.Petching.petching.tag.petSize.CarePost_PetSize;
import com.Petching.petching.tag.petSize.CarePost_PetSizeRepository;
import com.Petching.petching.tag.petSize.PetSize;
import com.Petching.petching.tag.petSize.PetSizeRepository;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    private final UserService userService;
    private final CarePostRepository repository;
    private final PetSizeRepository petSizeRepository;
    private final CarePost_PetSizeRepository carePostPetSizeRepository;
    private final JwtToken jwtToken;

    @PostMapping
    public ResponseEntity postCarePost(@Validated @RequestBody CarePostDto.Post post,
                                       @RequestHeader("Authorization") String authorization) {

        authorization = authorization.replaceAll("Bearer ","");
        User user = userService.findUser(jwtToken.extractUserIdFromToken(authorization));


        CarePost newPost = mapper.carePostPostDtoToCarePost(post);

        newPost.setUser(user);

        CarePost createdPost = service.savePost(newPost);

        if (post.getPetSizes() != null) {
            for (String tagName : post.getPetSizes()) {
                PetSize tag = petSizeRepository.findByBody(tagName);
                CarePost_PetSize pt = new CarePost_PetSize();
                if (tag == null) {
                    PetSize ct = new PetSize();
                    ct.setBody(tagName);
                    pt.setPetSize(ct);
                }
                if (pt.getPetSize() == null) {
                    pt.setPetSize(tag);
                }
                pt.setCarePost(createdPost);
                carePostPetSizeRepository.save(pt);
            }
        }
        URI uri = URICreator.createUri("/post", createdPost.getPostId());

        return ResponseEntity.created(uri).build();
    }

    @Transactional
    @PatchMapping("/{post-id}")
    public ResponseEntity patchCarePost(@PathVariable("post-id") @Positive long postId,
                                        @Validated @RequestBody CarePostDto.Patch patch,
                                        @RequestHeader("Authorization") String authorization) {

        authorization = authorization.replaceAll("Bearer ","");
        User user = userService.findUser(jwtToken.extractUserIdFromToken(authorization));


        CarePost newPatch = mapper.carePostPatchDtoToCarePost(patch);
        newPatch.setUser(user);

        CarePost updatedPost = service.updatePost(newPatch,postId);

        carePostPetSizeRepository.deleteAllByCarePost_PostId(postId);

        Optional.ofNullable(patch.getPetSizes()).ifPresent((TagList) -> {
            List<CarePost_PetSize> newCarePost_Pet_SizeList = new ArrayList<>();
            for (String tagTemp : patch.getPetSizes()) {  //태그 저장
                PetSize findTag = petSizeRepository.findByBody(tagTemp);
                if (findTag == null) {
                    PetSize newTag = new PetSize(tagTemp);
                    findTag =  petSizeRepository.save(newTag);
                }
                CarePost_PetSize newCarePost_PetSize = new CarePost_PetSize(updatedPost, findTag);

                carePostPetSizeRepository.save(newCarePost_PetSize);

                newCarePost_Pet_SizeList.add(newCarePost_PetSize);

            }
            updatedPost.setPostPetSizes(newCarePost_Pet_SizeList);

        });


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

    @GetMapping("/my-page/{user-id}")
    public ResponseEntity getMyPageCarePost (@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @PathVariable("user-id") @Positive long userId) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<CarePost> pageCare = service.findAllPost(pageable);
        List<CarePostDto.MyPage> find = mapper.carePostsToCarePostMyPageDto(
                service.findUserCarePost(userId
                ));

        return new ResponseEntity<>(new MultiResponse<>(find, pageCare),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity searchCarePost(
            @RequestParam("locationTag") String locationTag,
            @RequestParam("startDate.day") int startDay,
            @RequestParam("startDate.month") int startMonth,
            @RequestParam("startDate.year") int startYear,
            @RequestParam("endDate.day") int endDay,
            @RequestParam("endDate.month") int endMonth,
            @RequestParam("endDate.year") int endYear) {

        List<CarePost> searchPost = service.searchPost(
                locationTag, startDay, startMonth, startYear,
                endDay, endMonth, endYear
                );

        List<CarePostDto.Response> responseList = mapper.carePostsToCarePostResponseDtos(searchPost);

        return new ResponseEntity(new SingleResponse<>(responseList),HttpStatus.OK);
    }

    @DeleteMapping("/{post-id}")
    public ResponseEntity deleteCarePost(@PathVariable("post-id") @Positive long postId,
                                         @RequestHeader("Authorization") String authorization) {

        authorization = authorization.replaceAll("Bearer ","");
        User user = userService.findUser(jwtToken.extractUserIdFromToken(authorization));

        service.deletePost(postId,user.getUserId());

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
