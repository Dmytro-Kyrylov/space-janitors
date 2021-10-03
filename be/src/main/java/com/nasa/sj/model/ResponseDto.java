package com.nasa.sj.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ResponseDto {

    private List<? extends BaseDto> density = new LinkedList<>();

    private List<Long> highDangerActive = new LinkedList<>();

    private List<Long> lowDangerActive = new LinkedList<>();

}
