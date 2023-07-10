package com.Petching.petching.carepost.service;

import com.Petching.petching.tag.conditionTag.CarePost_ConditionTag;
import com.Petching.petching.tag.conditionTag.CarePost_ConditionTagRepository;
import com.Petching.petching.tag.conditionTag.ConditionTag;
import com.Petching.petching.tag.conditionTag.ConditionTagRepository;
import com.Petching.petching.tag.locationTag.CarePost_LocationTag;
import com.Petching.petching.tag.locationTag.CarePost_LocationTagRepository;
import com.Petching.petching.tag.locationTag.LocationTag;
import com.Petching.petching.tag.locationTag.LocationTagRepository;
import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.carepost.mapper.CarePostMapper;
import com.Petching.petching.carepost.repository.CarePostRepository;
import com.Petching.petching.exception.BusinessLogicException;
import com.Petching.petching.exception.ExceptionCode;
import com.Petching.petching.user.entity.User;
import com.Petching.petching.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CarePostService {
    private final CarePostRepository repository;
    private final CarePost_ConditionTagRepository carePostConditionTagRepository;
    private final CarePost_LocationTagRepository carePostLocationTagRepository;
    private final ConditionTagRepository conditionTagRepository;
    private final LocationTagRepository locationTagRepository;
    private final CarePostMapper mapper;
    private final UserService userService;

    public CarePost savePost(CarePostDto.Post post) {

        User user = userService.verifiedUser(post.getUserId());

        CarePost newPost = mapper.carePostPostDtoToCarePost(post);

        newPost.setUser(user);

        CarePost savePost = repository.save(newPost);

        if (post.getConditionTags() != null) {
            for (String tagName : post.getConditionTags()) {
                ConditionTag tag = conditionTagRepository.findByBody(tagName);
                CarePost_ConditionTag pt = new CarePost_ConditionTag();
                if (tag == null) {
                    ConditionTag ct = new ConditionTag();
                    ct.setBody(tagName);
                    pt.setConditionTag(ct);
                }
                if (pt.getConditionTag() == null) {
                    pt.setConditionTag(tag);
                }
                pt.setCarePost(savePost);
                carePostConditionTagRepository.save(pt);
            }
        }

        if (post.getLocationTags() != null) {
            for (String tagName : post.getLocationTags()) {
                LocationTag tag = locationTagRepository.findByBody(tagName);
                CarePost_LocationTag pt = new CarePost_LocationTag();
                if (tag == null) {
                    LocationTag ct = new LocationTag();
                    ct.setBody(tagName);
                    pt.setLocationTag(ct);
                }
                if (pt.getLocationTag() == null) {
                    pt.setLocationTag(tag);
                }
                pt.setCarePost(savePost);
                carePostLocationTagRepository.save(pt);
            }
        }

        return savePost;
    }

    //fixme 태그 업데이트 수정 요망!
    public CarePost updatePost(CarePostDto.Patch patch,Long postId)   {

        User user = userService.verifiedUser(patch.getUserId());

        CarePost findPost = repository.findByPostId(postId);

        verifiedPostUser(findPost, user.getUserId());

        Optional.ofNullable(patch.getTitle())
                .ifPresent(findPost::setTitle);
        Optional.ofNullable(patch.getContent())
                .ifPresent(findPost::setContent);
        Optional.ofNullable(patch.getImage())
                .ifPresent(findPost::setImage);
        Optional.ofNullable(patch.getStartDate())
                .ifPresent(findPost::setStartDate);
        Optional.ofNullable(patch.getEndDate())
                .ifPresent(findPost::setEndDate);

        carePostConditionTagRepository.deleteAllByCarePost_PostId(postId);
        carePostLocationTagRepository.deleteAllByCarePost_PostId(postId);


        // conditionTag
        Optional.ofNullable(patch.getConditionTags()).ifPresent((TagList) -> {
            List<CarePost_ConditionTag> newCarePost_Condition_TagList = new ArrayList<>();
            for (String tagTemp : patch.getConditionTags()) {  //태그 저장
                ConditionTag findTag = conditionTagRepository.findByBody(tagTemp);
                if (findTag == null) {
                    ConditionTag newTag = new ConditionTag(tagTemp);
                    findTag =  conditionTagRepository.save(newTag);
                }
                CarePost_ConditionTag newCarePost_Condition_Tag = new CarePost_ConditionTag(findPost, findTag);

                carePostConditionTagRepository.save(newCarePost_Condition_Tag);

                newCarePost_Condition_TagList.add(newCarePost_Condition_Tag);

            }
            findPost.setPostConditionTags(newCarePost_Condition_TagList);

        });

//        location tag
        Optional.ofNullable(patch.getLocationTags()).ifPresent((TagList) -> {


            List<CarePost_LocationTag> newCarePost_Location_TagList = new ArrayList<>();
            for (String tagTemp : patch.getLocationTags()) {  //태그 저장
                LocationTag findTag = locationTagRepository.findByBody(tagTemp);
                if (findTag == null) {
                    LocationTag newTag = new LocationTag(tagTemp);
                    findTag =  locationTagRepository.save(newTag);
                }
                CarePost_LocationTag newCarePost_Location_Tag = new CarePost_LocationTag(findPost, findTag);

                carePostLocationTagRepository.save(newCarePost_Location_Tag);

                newCarePost_Location_TagList.add(newCarePost_Location_Tag);

            }
            findPost.setPostLocationTags(newCarePost_Location_TagList);

        });
        return repository.save(findPost);
    }

    public CarePost findPost(long postId) {

        return repository.findByPostId(postId);

    }

    public List<CarePost> findAllPost() {

        return repository.findAll(Sort.by("postId").descending());
//        return repository.findAll();

    }

    public void deletePost (long postId,long userId) {

        CarePost post = existsPost(postId);

        verifiedPostUser(post, userId);

        repository.deleteById(postId);
    }

    public CarePost existsPost (long postId) {
        Optional<CarePost> optional = repository.findById(postId);
        CarePost findId = optional.orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));

        return findId;
    }

    public void verifiedPostUser(CarePost post, long userId) {
        if (post.getUser().getUserId() != userId) {
            throw new BusinessLogicException(ExceptionCode.POST_NOT_WRITE);
        }
    }

}
