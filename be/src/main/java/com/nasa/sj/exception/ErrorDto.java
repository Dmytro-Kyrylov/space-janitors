package com.nasa.sj.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class ErrorDto {

    private final ExceptionType type;

    private final String message;

    private Map<String, String> details;

    public ErrorDto(ExtException exception) {
        this.type = exception.getType();
        this.message = exception.getMessage();
        this.details = exception.getDetails();
    }

}
