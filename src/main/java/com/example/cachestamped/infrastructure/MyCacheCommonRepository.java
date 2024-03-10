package com.example.cachestamped.infrastructure;

import com.example.cachestamped.domain.MyCache;
import com.example.cachestamped.infrastructure.db.MyDataJpaRepository;
import com.example.cachestamped.infrastructure.redis.MyCacheRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyCacheCommonRepository {
    private final MyCacheRedisRepository myCacheRedisRepository;
    private final RedisTemplate<String, MyCache> redisTemplate;
    private final MyDataJpaRepository myDataJpaRepository;

    public MyCache findMyCache(int id) {
        return myDataJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found my cache"));
    }

    public void saveMyCache(MyCache myCache) {
        myDataJpaRepository.save(myCache);
    }
}
