package com.Petching.petching.question.controller;

import com.Petching.petching.global.api.jwt.service.ExtractService;
import com.Petching.petching.login.service.UserLoginService;
import com.Petching.petching.question.dto.QuestionDto;
import com.Petching.petching.question.entity.Question;
import com.Petching.petching.question.mapper.QuestionMapper;
import com.Petching.petching.question.service.QuestionService;
import com.Petching.petching.response.SingleResponse;
import com.Petching.petching.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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

/*
    @DeleteMapping("/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable("questionId") Long questionId,
                                         @RequestHeader("Authorization") String authorization){



    }

    @GetMapping("/{questionId}")
    public ResponseEntity getQuestion(@PathVariable("questionId") Long questionId,
                                      @RequestHeader("Authorization") String authorization){

    }

    @GetMapping
    public ResponseEntity getAllQuestion(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestHeader("Authorization") String authorization){

    }
*/

}
