package com.Petching.petching.question.service;

import com.Petching.petching.board.entity.Board;
import com.Petching.petching.global.api.jwt.service.ExtractService;
import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.question.entity.Question;
import com.Petching.petching.question.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final ExtractService extractService;

    public Question create(Question question) {
        return questionRepository.save(question);
    }

    public Question update(Question request) {
        Question original = findQuestion(request.getQuestionId());

        extractService.isSameUser(request.getUser(), original.getUser());

        Optional.ofNullable(request.getTitle())
                .ifPresent(title -> original.setTitle(title));
        Optional.ofNullable(request.getContent())
                .ifPresent(content -> original.setContent(content));
        Optional.ofNullable(request.getImgUrls())
                .ifPresent(imgUrls -> original.setImgUrls(imgUrls));
        Optional.ofNullable(request.getQuestionType())
                .ifPresent(questionType -> original.setQuestionType(questionType));

        return questionRepository.save(original);
    }


    public void delete(Question request) {

        Question original = findQuestion(request.getQuestionId());
        extractService.isSameUser(request.getUser(), original.getUser());

        questionRepository.deleteById(request.getQuestionId());
    }


    public Question findQuestion(Long questionId) {
        return verifyQuestion(questionId);
    }

    public Page<Question> findQuestions(Pageable pageable) {

        return questionRepository.findAll(pageable);
    }

    public Question verifyQuestion (Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        return optionalQuestion.orElseThrow(
                ()-> new BusinessLogicException(ExceptionCode.CONTENT_NOT_FOUND)
        );
    }

    public Page<Question> findQuestionByWrittenUser(long userId, Pageable pageable) {

        return questionRepository.findQuestionsByUserId(userId, pageable);
    }


}
