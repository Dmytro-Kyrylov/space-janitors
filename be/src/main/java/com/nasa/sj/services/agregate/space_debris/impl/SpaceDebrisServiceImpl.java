package com.nasa.sj.services.agregate.space_debris.impl;

import com.nasa.sj.model.BaseDto;
import com.nasa.sj.model.ResponseDto;
import com.nasa.sj.services.agregate.space_debris.SpaceDebrisService;
import com.nasa.sj.services.cosmos.CosmosService;
import com.nasa.sj.services.cosmos.model.CosmosDto;
import com.nasa.sj.services.iridium.IridiumService;
import com.nasa.sj.services.iridium.model.IridiumDto;
import com.nasa.sj.services.ml.MLService;
import com.nasa.sj.services.ml.model.DatetimeDataHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class SpaceDebrisServiceImpl implements SpaceDebrisService {

    private final MLService mlService;

    private final IridiumService iridiumService;

    private final CosmosService cosmosService;

    @Override
    public ResponseDto getAllByNearestDatetimeWithDangerData(LocalDateTime dateTime) {
        ResponseDto responseDto = new ResponseDto();

        List<BaseDto> allByNearestDatetime = new LinkedList<>();

        List<IridiumDto> allByNearestDatetimeIridium = iridiumService.getAllByNearestDatetime(dateTime);
        List<CosmosDto> allByNearestDatetimeCosmos = cosmosService.getAllByNearestDatetime(dateTime);

        allByNearestDatetime.addAll(allByNearestDatetimeIridium);
        allByNearestDatetime.addAll(allByNearestDatetimeCosmos);

        Map<LocalDateTime, DatetimeDataHolder> colorsByDatetime = mlService.getColorsByDatetime(dateTime);

        DatetimeDataHolder dataByDatetime = colorsByDatetime.getOrDefault(dateTime, new DatetimeDataHolder());
        responseDto.setHighDangerActive(dataByDatetime.getHighDangerActive());
        responseDto.setLowDangerActive(dataByDatetime.getLowDangerActive());

        Map<Long, DatetimeDataHolder.ColorHolder> colorByNorad = dataByDatetime.getDensity()
                .stream()
                .collect(Collectors.toMap(DatetimeDataHolder.ColorHolder::getNorad, x -> x));

        allByNearestDatetime.forEach(x -> x.setColor(
                colorByNorad.getOrDefault(x.getNorad(), new DatetimeDataHolder.ColorHolder()).getColor())
        );

        responseDto.setDensity(allByNearestDatetime);

        return responseDto;
    }

}
