package com.nasa.sj.services.active.impl;

import com.nasa.sj.services.active.ActiveService;
import com.nasa.sj.services.active.model.ActiveDto;
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
class ActiveController {

    private final ActiveService service;

    @GetMapping("/active")
    public List<ActiveDto> find(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return service.getAllByNearestDatetime(dateTime);
    }

}
