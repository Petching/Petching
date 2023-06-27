package com.Petching.petching.carepost.service;

import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.carepost.mapper.CarePostMapper;
import com.Petching.petching.carepost.repository.CarePostRepository;
import com.Petching.petching.conditionTag.CarePost_ConditionTag;
import com.Petching.petching.conditionTag.CarePost_ConditionTagRepository;
import com.Petching.petching.conditionTag.ConditionTag;
import com.Petching.petching.conditionTag.ConditionTagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CarePostService implements Cloneable {

    @PersistenceContext
    private EntityManager entityManager;
    private final CarePostRepository repository;
    private final CarePost_ConditionTagRepository carePostConditionTagRepository;
    private final ConditionTagRepository conditionTagRepository;
    private final CarePostMapper mapper;

    public CarePost savePost(CarePostDto.Post post) {

        CarePost newPost = mapper.carePostPostDtoToCarePost(post);

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

        return savePost;
    }

    //fixme 태그 업데이트 수정 요망!
    public CarePost updatePost(CarePostDto.Patch patch,Long postId)   {
        CarePost findPost = repository.findByPostId(postId);

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

        //fixme 수정!!
        Optional.ofNullable(patch.getConditionTags()).ifPresent((TagList) -> {

            carePostConditionTagRepository.deleteAllByCarePost_PostId(postId);
            List<CarePost_ConditionTag> newCarePost_ConditionTagList = new ArrayList<>();
            for (String tagTemp : patch.getConditionTags()) {  //태그 저장
                ConditionTag findTag = conditionTagRepository.findByBody(tagTemp);
                if (findTag == null) {
                    ConditionTag newTag = new ConditionTag(tagTemp);
                    findTag =  conditionTagRepository.save(newTag);
                }
                CarePost_ConditionTag newCarePost_ConditionTag = new CarePost_ConditionTag(findPost, findTag);

                carePostConditionTagRepository.save(newCarePost_ConditionTag);

                newCarePost_ConditionTagList.add(newCarePost_ConditionTag);

            }
//            findPost.getPostConditionTags().clear();
//            findPost.getPostConditionTags().addAll(newCarePost_ConditionTagList);
            findPost.setPostConditionTags(newCarePost_ConditionTagList);

        });
        return repository.save(findPost);
    }



//        carePostConditionTagRepository.deleteAllByCarePost_PostId(postId);
//
////        findPost = entityManager.merge(findPost);
//
//        if (patch.getConditionTags() != null) {
//            for (String tagName : patch.getConditionTags()) {
//                ConditionTag tag = conditionTagRepository.findByBody(tagName);
//                CarePost_ConditionTag pt = new CarePost_ConditionTag();
//                if (tag == null) {
//                    ConditionTag ct = new ConditionTag();
//                    ct.setBody(tagName);
//                    pt.setConditionTag(ct);
//                }
//                if (pt.getConditionTag() == null) {
//                    pt.setConditionTag(tag);
//                }
//                pt.setCarePost(findPost);
//
//                carePostConditionTagRepository.save(pt);
//            }
//        }


//        return repository.save(findPost);
//    }


//        CarePost_ConditionTag postTag = new CarePost_ConditionTag();
//
//        if (patch.getConditionTags() != null) {
//            List<CarePost_ConditionTag> existingTags = new ArrayList<>(findPost.getPostTags());
//            List<String> newTags = patch.getConditionTags();
//
//            existingTags.removeIf(tag -> !newTags.contains(tag.getConditionTag().getBody()));
//
//            for (String tagBody : newTags) {
//                boolean tagExists = existingTags.stream()
//                        .anyMatch(tag -> tag.getConditionTag().getBody().equals(tagBody));
//
//                if (!tagExists) {
//                    ConditionTag tag = conditionTagRepository.findByBody(tagBody);
//                    if (tag == null) {
//                        tag = new ConditionTag();
//                        tag.setBody(tagBody);
//                        postTag.setConditionTag(tag);
//                    }
//                    postTag.setCarePost(findPost);
//
//                    postTag = carePostConditionTagRepository.save(postTag);
//                    existingTags.add(postTag);
//
//                }
//            }
//            findPost.setPostTags(existingTags);
//        }

//        return repository.save(findPost);
//    }

    public CarePost findPost(long postId) {

        return repository.findByPostId(postId);

    }

    public List<CarePost> findAllPost() {

        return repository.findAll(Sort.by("postId").descending());

    }

    public void deletePost (long postId) {

        repository.deleteById(postId);
    }

}
