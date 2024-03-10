package com.example.test;

import com.example.cachestamped.CacheStampedApplication;
import com.example.cachestamped.application.MyCacheService;
import com.example.cachestamped.domain.MyCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CacheStampedApplication.class)
public class CacheStampedeTest {
    @Autowired
    private MyCacheService myCacheService;

    @Test
    public void 스탬피드_현상_체크() throws InterruptedException {
        myCacheService.saveMyData(MyCache.builder()
                .id(1)
                .cacheMissCount(0)
                .build());

        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < 100000; i++) {
            executorService.submit(() -> {
                try {
                    myCacheService.findMyCache(1);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        Thread.sleep(1000);

        assertThat(myCacheService.getCacheMissCount()).isEqualTo(0);
    }

    @Test
    public void 선계산으로_캐시_스탬피드_현상_줄이기() {
        myCacheService.saveMyData(MyCache.builder()
                .id(1)
                .cacheMissCount(0)
                .build());
    }
}
