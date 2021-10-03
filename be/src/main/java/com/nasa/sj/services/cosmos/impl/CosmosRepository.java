package com.nasa.sj.services.cosmos.impl;

import com.nasa.sj.config.CacheConfig;
import com.nasa.sj.services.cosmos.model.CosmosDto;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CosmosRepository {

    private final DSLContext dsl;

    @Cacheable(value = CacheConfig.COSMOS_CACHE_BY_DATE_TIME, keyGenerator = "localDateTimeKeyGenerator")
    public List<CosmosDto> getAllByNearestDatetime(LocalDateTime dateTime) {
        return dsl.fetch("with min_time_diff_by_norad as (select norad,\n" +
                        "                                       min(abs(extract(epoch from (utc_datetime - ?::timestamp)))) as min_time_diff\n" +
                        "                                from cosmos\n" +
                        "                                group by norad)\n" +
                        "select i.id,\n" +
                        "       i.norad,\n" +
                        "       i.latitude_deg,\n" +
                        "       i.longitude_deg,\n" +
                        "       i.height,\n" +
                        "       i.utc_datetime,\n" +
                        "       i.tle1,\n" +
                        "       i.tle2\n" +
                        "from cosmos i\n" +
                        "         inner join min_time_diff_by_norad m\n" +
                        "                    on i.norad = m.norad and\n" +
                        "                       abs(extract(epoch from (i.utc_datetime - ?::timestamp))) = m.min_time_diff;", dateTime, dateTime)
                .into(CosmosDto.class);
    }

}
