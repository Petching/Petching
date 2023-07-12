package com.Petching.petching.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.Petching.petching.global.exception.ExceptionCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;


    // DTO 멤버 변수 필드의 유효성 검증 실패로 발생한 에러 정버를 담는 멤버 변수
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<FieldError> fieldErrors;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private CustomData customData;


    // URI 변수 값의 유효성 검증 실패로 발생한 에러 정버를 담는 멤버 변수
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ConstraintViolationError> violationErrors;

    // 아래 of 메서드를 이용해서 ErrorResponse 객체를 생성할 것이기 때문에 생성자 임에도 불구하고 private
    private ErrorResponse(List<FieldError> fieldErrors, List<ConstraintViolationError> violationErrors) {
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
    }
    private ErrorResponse(Integer status, String message){
        this.status = status;
        this.message = message;
    }

    private ErrorResponse(CustomData data){
        this.customData = data;
    }
    // BindingResult 에 대한 ErrorResponse 객체를 생성하기 위해 FieldError 에게 전달시켜 fieldError 를 받아옴.
    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(FieldError.of(bindingResult), null);
    }

    // ConstraintViolationException 에 대한 에러 정보를 얻기 위해선 Set<ConstraintViolation<?>> 가 필요하다.
    // Set<ConstraintViolation<?>> 에 대한 ErrorResponse 객체를 생성하기 위해 ConstraintViolationError 에게 전달시켜 violationErrors 를 받아옴.
    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
        return new ErrorResponse(null, ConstraintViolationError.of(violations));
    }

    public static ErrorResponse of(ExceptionCode exceptionCode){
        return new ErrorResponse(exceptionCode.getStatus(), exceptionCode.getMessage());
    }

    public static ErrorResponse of(HttpStatus httpStatus){

        return new ErrorResponse(httpStatus.value(), httpStatus.getReasonPhrase());
    }

    public static ErrorResponse of(Integer status, String message){
        return new ErrorResponse(status, message);
    }

    public static ErrorResponse of(ErrorResponse info, Map<String, String> message) {
        return new ErrorResponse(new ErrorResponse.CustomData(info, message));
    }
    // Field Error 가공 : DTO 클래스의 유효성 검증에서 발생하는 에러 정보 생성
    @Getter
    public static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;

        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors =
                    bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ?
                                    "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }

    // ConstraintViolation Error 가공 : URI 변수 값에 대한 에러 정보를 생성
    @Getter
    public static class ConstraintViolationError {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        private ConstraintViolationError(String propertyPath, Object rejectedValue,
                                         String reason) {
            this.propertyPath = propertyPath;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<ConstraintViolationError> of(
                Set<ConstraintViolation<?>> constraintViolations) {
            return constraintViolations.stream()
                    .map(constraintViolation
                            -> new ConstraintViolationError(
                                        constraintViolation.getPropertyPath().toString(),
                                        constraintViolation.getInvalidValue().toString(),
                                        constraintViolation.getMessage()
                                          ))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    public static class CustomData {
        private final ErrorResponse info;
        private final Map<String, String> message;

        public CustomData(ErrorResponse info, Map<String, String> message) {
            this.info = info;
            this.message = message;
        }
    }
}
