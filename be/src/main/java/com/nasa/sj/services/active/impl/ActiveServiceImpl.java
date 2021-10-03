package com.nasa.sj.services.active.impl;

import com.nasa.sj.services.active.ActiveService;
import com.nasa.sj.services.active.model.ActiveDto;
import com.nasa.sj.services.cosmos.impl.CosmosRepository;
import com.nasa.sj.services.cosmos.model.CosmosDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class ActiveServiceImpl implements ActiveService {

    private final ActiveRepository repository;

    @Override
    public List<ActiveDto> getAllByNearestDatetime(LocalDateTime dateTime) {
        return repository.getAllByNearestDatetime(dateTime);
    }

}
