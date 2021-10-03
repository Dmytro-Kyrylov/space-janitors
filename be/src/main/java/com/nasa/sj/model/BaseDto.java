package com.nasa.sj.model;

import lombok.Data;

@Data
public abstract class BaseDto {

    private String color;

    private Long norad;

    private String latitudeDeg;

    private String longitudeDeg;

    private String tle1;

    private String tle2;

    private Double height;

    private String utcDatetime;

}
