package com.Petching.petching.question.controller;

import com.Petching.petching.global.api.jwt.service.ExtractService;
import com.Petching.petching.question.dto.QuestionDto;
import com.Petching.petching.question.entity.Question;
import com.Petching.petching.question.mapper.QuestionMapper;
import com.Petching.petching.question.service.QuestionService;
import com.Petching.petching.response.MultiResponse;
import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionMapper questionMapper;
    private final QuestionService questionService;
    private final ExtractService extractService;

    @PostMapping
    public ResponseEntity postQuestion(@RequestBody QuestionDto.Post requestBody,
                                    @RequestHeader("Authorization") String authorization){

        User user = extractService.getUserFromToken(authorization);

        Question request = questionMapper.postDtoToEntity(requestBody);
        request.setUser(user);
        request = questionService.create(request);

        URI uri = UriComponentsBuilder.newInstance()
                .path("/questions/"+request.getQuestionId())
                .build().toUri();

        return ResponseEntity.created(uri).build();
    }


    @PatchMapping("/{questionId}")
    public ResponseEntity patchQuestion(@PathVariable("questionId") Long questionId,
                                        @RequestBody QuestionDto.Patch requestBody,
                                        @RequestHeader("Authorization") String authorization){

        User user = extractService.getUserFromToken(authorization);
        Question request = questionMapper.patchDtoToEntity(requestBody);
        request.setUser(user);
        request.setQuestionId(questionId);

        request = questionService.update(request);

        QuestionDto.Detail detail = questionMapper.entityToDetail(request);

        return new ResponseEntity<>(new SingleResponse<>(detail),HttpStatus.OK);
    }


    @DeleteMapping("/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable("questionId") Long questionId,
                                         @RequestHeader("Authorization") String authorization){

        User user = extractService.getUserFromToken(authorization);

        Question request = Question.builder().build();
        request.setUser(user);
        request.setQuestionId(questionId);

        questionService.delete(request);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity getQuestion(@PathVariable("questionId") Long questionId){

        Question request = questionService.findQuestion(questionId);
        QuestionDto.Detail detail = questionMapper.entityToDetail(request);

        return new ResponseEntity<>(new SingleResponse<>(detail), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity getAllQuestions(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page -1, size);

        Page<Question> questionPage =
                questionService.findQuestions(pageable);

        List<QuestionDto.Response> responses =
                questionMapper.questionPageToQuestionResponseListDto(questionPage);

        return new ResponseEntity<>(new MultiResponse<>(responses, questionPage), HttpStatus.OK);
    }


    @GetMapping("/{userId}/written")
    public ResponseEntity getUserWrittenQuestions(@PathVariable("userId") Long userId,
                                                  @RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page -1, size);

        Page<Question> writtenQuestion =
                questionService.findQuestionByWrittenUser(userId, pageable);

        List<QuestionDto.Response> result =
                questionMapper.questionPageToQuestionResponseListDto(writtenQuestion);


        return new ResponseEntity<>(new MultiResponse<>(result, writtenQuestion), HttpStatus.OK);
    }


}
