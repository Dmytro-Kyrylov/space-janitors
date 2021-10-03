package com.nasa.sj.config;

import com.nasa.sj.model.LocalDateTimeKeyGenerator;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@org.springframework.context.annotation.Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    public static final String COLOR_BY_NORAD_AND_TIME = "colorCache";

    public static final String IRIDIUM_CACHE_BY_DATE_TIME = "debrisCache";

    public static final String ACTIVE_CACHE_BY_DATE_TIME = "activeCache";

    public static final String COSMOS_CACHE_BY_DATE_TIME = "cosmosCache";

    private final Configuration<Object, Object> jcacheOneMinutesConfiguration;

    private final Configuration<Object, Object> jcacheTenMinutesConfiguration;

    public CacheConfig() {
        jcacheOneMinutesConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(1000))
                        .withExpiry(
                                ExpiryPolicyBuilder.timeToLiveExpiration(Duration.of(1, ChronoUnit.MINUTES))
                        )
                        .build()
        );
        jcacheTenMinutesConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(1000))
                        .withExpiry(
                                ExpiryPolicyBuilder.timeToLiveExpiration(Duration.of(10, ChronoUnit.MINUTES))
                        )
                        .build()
        );
    }

    @Bean("localDateTimeKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new LocalDateTimeKeyGenerator();
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, COLOR_BY_NORAD_AND_TIME, jcacheTenMinutesConfiguration);
            createCache(cm, IRIDIUM_CACHE_BY_DATE_TIME, jcacheOneMinutesConfiguration);
            createCache(cm, ACTIVE_CACHE_BY_DATE_TIME, jcacheOneMinutesConfiguration);
            createCache(cm, COSMOS_CACHE_BY_DATE_TIME, jcacheOneMinutesConfiguration);
        };
    }

    private void createCache(CacheManager cm, String cacheName, Configuration<Object, Object> configuration) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, configuration);
        }
    }

}
