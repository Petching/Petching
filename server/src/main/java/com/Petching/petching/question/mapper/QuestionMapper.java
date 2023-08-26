package com.Petching.petching.question.mapper;

import com.Petching.petching.question.dto.QuestionDto;
import com.Petching.petching.question.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question postDtoToEntity(QuestionDto.Post requestBody);
    Question patchDtoToEntity(QuestionDto.Patch requestBody);

    QuestionDto.Detail entityToDetail(Question question);

    QuestionDto.Response entityToResponse(Question question);


}
