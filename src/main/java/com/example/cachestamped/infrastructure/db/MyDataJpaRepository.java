package com.example.cachestamped.infrastructure.db;

import com.example.cachestamped.domain.MyCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyDataJpaRepository extends JpaRepository<MyCache, Integer> {
}
