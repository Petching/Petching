package com.Petching.petching.global.advice;

import com.Petching.petching.global.exception.BusinessLogicException;
import com.Petching.petching.global.exception.ExceptionCode;
import com.Petching.petching.global.response.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {

        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

    // handleBindException
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {

        log.error("# handleBindException", e);

        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // handleMultipartException
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity handleMultipartException(HttpMediaTypeNotSupportedException e) {

        log.error("# handleMultipartException", e);

        final ErrorResponse response = ErrorResponse.of(ExceptionCode.UNSUPPORTED_MEDIA_TYPE);

        Map<String, String > info = new HashMap<>();
        info.put("errorMessage", e.getMessage());
        info.put("requestContentType", String.valueOf(e.getContentType()));
        info.put("supportContentType", e.getSupportedMediaTypes().toString().replaceAll("\\[","").replaceAll("]",""));

        return new ResponseEntity<>(ErrorResponse.of(response, info), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    // handleRequestMethodNotSupportedException
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request){

        log.error("# handleRequestMethodNotSupportedException", e);

        final ErrorResponse response = ErrorResponse.of(ExceptionCode.METHOD_NOT_ALLOWED);

        Map<String, String > info = new HashMap<>();
        info.put("errorMessage", e.getMessage());
        info.put("requestEndPoint", String.valueOf(request.getRequestURI()));
        info.put("requestMethod", e.getMethod());

        return new ResponseEntity<>(ErrorResponse.of(response , info), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {

        log.error("# handleBusinessLogicException", e);

        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity<>(response,HttpStatus.valueOf(e.getExceptionCode().getStatus()));
    }


    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {

        log.error("# handle Exception", e);

        final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);

        Map<String, String > info = new HashMap<>();
        info.put("errorMessage", e.getMessage());

        return new ResponseEntity<>(ErrorResponse.of(response , info), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // #################################################################

    @ExceptionHandler({InternalAuthenticationServiceException.class,
            IllegalArgumentException.class,
    })
    public ResponseEntity handleCommonException(
            Exception e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST);

        Map<String, String > info = new HashMap<>();
        info.put("errorMessage", e.getMessage());

        return new ResponseEntity<>(ErrorResponse.of(response , info), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST);

        Map<String, String > info = new HashMap<>();
        info.put("errorMessage", e.getMessage());
        info.put("httpInputMessage", e.getHttpInputMessage().toString());

        return new ResponseEntity<>(ErrorResponse.of(response , info), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity handleInvalidFormatException(
            InvalidFormatException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST);

        Map<String, String > info = new HashMap<>();
        info.put("errorMessage", e.getMessage());
        info.put("value", e.getValue().toString());

        return new ResponseEntity<>(ErrorResponse.of(response , info), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST);

        Map<String, String > info = new HashMap<>();
        info.put("errorMessage", e.getMessage());
        info.put("parameterName", e.getParameterName());
        info.put("parameterType", e.getParameterType());

        return new ResponseEntity<>(ErrorResponse.of(response , info), HttpStatus.BAD_REQUEST);
    }




}
