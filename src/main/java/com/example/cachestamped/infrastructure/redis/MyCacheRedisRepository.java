package com.example.cachestamped.infrastructure.redis;

import com.example.cachestamped.domain.MyCache;
import org.springframework.data.repository.CrudRepository;

public interface MyCacheRedisRepository extends CrudRepository<MyCache, String> {
}
