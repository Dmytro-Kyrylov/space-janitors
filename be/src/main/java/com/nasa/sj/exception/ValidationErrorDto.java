package com.nasa.sj.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorDto {

    private String message;

    private Object value;

    private String field;

}
