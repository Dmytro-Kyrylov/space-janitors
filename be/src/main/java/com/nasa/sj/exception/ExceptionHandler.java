package com.nasa.sj.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NullableProblems")
@Slf4j
@RestController
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ExtException.class)
    public ResponseEntity<ErrorDto> handleValidationException(ExtException exception) {
        ErrorDto errorDto = new ErrorDto(exception);
        return new ResponseEntity<>(errorDto, exception.getType().getStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleValidationException(Exception exception) {
        log.error(exception.getMessage(), exception);

        ErrorDto errorDto = new ErrorDto(ExceptionType.UNKNOWN, exception.getMessage());
        return new ResponseEntity<>(errorDto, ExceptionType.UNKNOWN.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        List<ValidationErrorDto> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                Object value = ((FieldError) error).getRejectedValue();
                String errorMessage = error.getDefaultMessage();

                errors.add(new ValidationErrorDto(errorMessage, value, fieldName));
            } else if (error instanceof ObjectError) {
                errors.add(new ValidationErrorDto(error.getDefaultMessage(), null, null));
            }
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        ErrorDto errorDto = new ErrorDto(ExceptionType.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ErrorDto errorDto = new ErrorDto(ExceptionType.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        ErrorDto errorDto = new ErrorDto(ExceptionType.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

}