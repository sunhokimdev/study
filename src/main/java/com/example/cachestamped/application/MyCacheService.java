package com.example.cachestamped.application;

import com.example.cachestamped.domain.MyCache;
import com.example.cachestamped.infrastructure.MyCacheCommonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Service
@RequiredArgsConstructor
public class MyCacheService {
    private final MyCacheCommonRepository myCacheCommonRepository;
    private final AtomicInteger cacheMissCount = new AtomicInteger(0);

    @Transactional(readOnly = true)
    @Cacheable(value = "MyData", key = "#id")
    public MyCache findMyCache(int id) {
        log.info("cache miss count: {}", cacheMissCount.incrementAndGet());
        return myCacheCommonRepository.findMyCache(id);
    }

    @Transactional
    @CachePut(value = "MyData", key = "#myCache.id")
    public void saveMyData(MyCache myCache) {
        cacheMissCount.set(0);
        myCacheCommonRepository.saveMyCache(myCache);
    }

    public int getCacheMissCount() {
        return cacheMissCount.get();
    }
}
