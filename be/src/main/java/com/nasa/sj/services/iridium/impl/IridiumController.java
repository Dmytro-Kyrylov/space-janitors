package com.nasa.sj.services.iridium.impl;

import com.nasa.sj.services.iridium.IridiumService;
import com.nasa.sj.services.iridium.model.IridiumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
class IridiumController {

    private final IridiumService service;

    @GetMapping("/iridium")
    public List<IridiumDto> find(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return service.getAllByNearestDatetime(dateTime);
    }

}
