package ru.otus.product;

import lombok.Getter;
import lombok.Setter;
import org.ehcache.core.statistics.CacheStatistics;

import java.io.Serializable;

@Getter
@Setter
public class CacheStatisticsDto implements Serializable {

    private long cachePuts;
    private long cacheGets;
    private long cacheRemovals;
    private long cacheEvictions;
    private long cacheExpirations;
    private long cacheHits;
    private float cacheHitPercentage;
    private long cacheMisses;
    private float cacheMissPercentage;

    public CacheStatisticsDto(CacheStatistics cacheStatistics) {
        cachePuts = cacheStatistics.getCachePuts();
        cacheGets = cacheStatistics.getCacheGets();
        cacheRemovals = cacheStatistics.getCacheRemovals();
        cacheEvictions = cacheStatistics.getCacheEvictions();
        cacheExpirations = cacheStatistics.getCacheExpirations();
        cacheHits = cacheStatistics.getCacheHits();
        cacheHitPercentage = cacheStatistics.getCacheHitPercentage();
        cacheMisses = cacheStatistics.getCacheMisses();
        cacheMissPercentage = cacheStatistics.getCacheMissPercentage();
    }

    public static CacheStatisticsDto fromCacheStatistics(CacheStatistics cacheStatistics) {
        return new CacheStatisticsDto(cacheStatistics);
    }
}
