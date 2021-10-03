package com.nasa.sj.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    UNKNOWN("Unknown error", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("Bad request", HttpStatus.BAD_REQUEST),
    ACCESS_DENIED("Insufficient privileges", HttpStatus.FORBIDDEN);

    @JsonValue
    private final String message;

    private final HttpStatus status;

    public ExtException exception() {
        return ExtException.of(this).build();
    }

}