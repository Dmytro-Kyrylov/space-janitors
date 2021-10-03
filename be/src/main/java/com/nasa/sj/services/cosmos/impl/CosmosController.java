package com.nasa.sj.services.cosmos.impl;

import com.nasa.sj.services.cosmos.CosmosService;
import com.nasa.sj.services.cosmos.model.CosmosDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
class CosmosController {

    private final CosmosService service;

    @GetMapping("/cosmos")
    public List<CosmosDto> find(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return service.getAllByNearestDatetime(dateTime);
    }

}
