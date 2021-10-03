package com.nasa.sj.services.ml.impl;

import com.nasa.sj.config.CacheConfig;
import com.nasa.sj.services.ml.MLService;
import com.nasa.sj.services.ml.model.DatetimeDataHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
class MLServiceImpl implements MLService {

    @Value("${app.ml.url}")
    private String mlServiceUrl;

    private final WebClient webClient;

    @Cacheable(value = CacheConfig.COLOR_BY_NORAD_AND_TIME)
    @Override
    public Map<LocalDateTime, DatetimeDataHolder> getColorsByDatetime(LocalDateTime dateTime) {
        return webClient
                .get()
                .uri(mlServiceUrl + "/api/v1/density?date=" + dateTime.toString())
                .retrieve()
                .toEntity(new ParameterizedTypeReference<Map<LocalDateTime, DatetimeDataHolder>>() {
                          }
                ).block()
                .getBody();
    }

}
