package com.nasa.sj.services.iridium.impl;

import com.nasa.sj.services.iridium.IridiumService;
import com.nasa.sj.services.iridium.model.IridiumDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
class IridiumServiceImpl implements IridiumService {

    private final IridiumRepository repository;

    @Override
    public List<IridiumDto> getAllByNearestDatetime(LocalDateTime dateTime) {
        return repository.getAllByNearestDatetime(dateTime);
    }

}
