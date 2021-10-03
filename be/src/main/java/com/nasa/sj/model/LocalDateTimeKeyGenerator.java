package com.nasa.sj.model;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return ((LocalDateTime) params[0]).truncatedTo(ChronoUnit.MINUTES);
    }

}
