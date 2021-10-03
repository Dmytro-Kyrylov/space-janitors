package com.nasa.sj.services.agregate.space_debris;

import com.nasa.sj.model.ResponseDto;

import java.time.LocalDateTime;

public interface SpaceDebrisService {
    ResponseDto getAllByNearestDatetimeWithDangerData(LocalDateTime dateTime);
}
