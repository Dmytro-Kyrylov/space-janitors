package com.nasa.sj.services.cosmos.impl;

import com.nasa.sj.services.cosmos.CosmosService;
import com.nasa.sj.services.cosmos.model.CosmosDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class CosmosServiceImpl implements CosmosService {

    private final CosmosRepository repository;

    @Override
    public List<CosmosDto> getAllByNearestDatetime(LocalDateTime dateTime) {
        return repository.getAllByNearestDatetime(dateTime);
    }

}
