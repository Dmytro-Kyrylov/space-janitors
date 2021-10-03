package com.nasa.sj.services.iridium;

import com.nasa.sj.services.iridium.model.IridiumDto;

import java.time.LocalDateTime;
import java.util.List;

public interface IridiumService {

    List<IridiumDto> getAllByNearestDatetime(LocalDateTime dateTime);

}
