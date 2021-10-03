package com.nasa.sj.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Singular;

import java.util.Map;

@Data
@Builder(builderMethodName = "innerBuilder")
@EqualsAndHashCode(callSuper = false)
public class ExtException extends RuntimeException {

    private final ExceptionType type;

    private String message;

    @Singular
    private Map<String, String> details;

    public static ExtExceptionBuilder of(ExceptionType type) {
        return innerBuilder().type(type);
    }

    @Override
    public String getMessage() {
        if (message == null) {
            return type.getMessage();
        }
        return message;
    }

}
