package com.nasa.sj.services.active;

import com.nasa.sj.services.active.model.ActiveDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ActiveService {

    List<ActiveDto> getAllByNearestDatetime(LocalDateTime dateTime);

}
