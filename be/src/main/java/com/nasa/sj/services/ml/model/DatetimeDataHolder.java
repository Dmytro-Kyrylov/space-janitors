package com.nasa.sj.services.ml.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class DatetimeDataHolder {

    private List<ColorHolder> density = new LinkedList<>();

    @JsonProperty("high_danger_active")
    private List<Long> highDangerActive = new LinkedList<>();

    @JsonProperty("low_danger_active")
    private List<Long> lowDangerActive = new LinkedList<>();

    @Data
    public static class ColorHolder {

        private String color;

        private Long norad;

    }

}
