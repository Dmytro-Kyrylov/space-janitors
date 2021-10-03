package com.nasa.sj.services.cosmos;

import com.nasa.sj.services.cosmos.model.CosmosDto;
import com.nasa.sj.services.iridium.model.IridiumDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CosmosService {

    List<CosmosDto> getAllByNearestDatetime(LocalDateTime dateTime);

}
