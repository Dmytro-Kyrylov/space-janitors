package com.nasa.sj.services.agregate.space_debris.impl;

import com.nasa.sj.model.ResponseDto;
import com.nasa.sj.services.agregate.space_debris.SpaceDebrisService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
class SpaceDebrisController {

    private final SpaceDebrisService spaceDebrisService;

    @GetMapping("/space-debris")
    public ResponseDto find(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        return spaceDebrisService.getAllByNearestDatetimeWithDangerData(dateTime);
    }

}
