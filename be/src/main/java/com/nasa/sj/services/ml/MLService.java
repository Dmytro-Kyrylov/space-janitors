package com.nasa.sj.services.ml;

import com.nasa.sj.services.ml.model.DatetimeDataHolder;

import java.time.LocalDateTime;
import java.util.Map;

public interface MLService {

    Map<LocalDateTime, DatetimeDataHolder> getColorsByDatetime(LocalDateTime dateTime);

}
