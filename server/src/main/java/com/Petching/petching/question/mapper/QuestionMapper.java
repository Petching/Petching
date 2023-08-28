package com.Petching.petching.question.mapper;

import com.Petching.petching.question.dto.QuestionDto;
import com.Petching.petching.question.entity.Question;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question postDtoToEntity(QuestionDto.Post requestBody);
    Question patchDtoToEntity(QuestionDto.Patch requestBody);

    QuestionDto.Detail entityToDetail(Question question);

    QuestionDto.Response entityToResponse(Question question);

    default List<QuestionDto.Response> questionPageToQuestionResponseListDto(Page<Question> writtenBoard){

        if(writtenBoard == null){
            return null;
        }

        List<QuestionDto.Response> responses = writtenBoard.stream()
                .map(question->entityToResponse(question))
                .collect(Collectors.toList());

        return responses;
    }



}
