package com.example.study.common.task;

import com.example.study.common.utils.CustomSpringELParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {
    private final RedissonClient redissonClient;

    @Around("@annotation(com.example.study.common.task.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DistributedLock classScheduleCloseDistributedLock = method.getAnnotation(DistributedLock.class);

        String redisKey = classScheduleCloseDistributedLock.lockPrefix() + CustomSpringELParser.getDynamicValue(methodSignature.getParameterNames(), joinPoint.getArgs(), classScheduleCloseDistributedLock.key());
        RLock redissonClientLock = redissonClient.getLock(redisKey);

        try {
            boolean tryLock = redissonClientLock.tryLock(classScheduleCloseDistributedLock.waitTime(), classScheduleCloseDistributedLock.leaseTime(), classScheduleCloseDistributedLock.timeUnit());
            if (!tryLock) {
                log.info(">>>>>>>>>>>>>> 이미 처리 중인 Lock({}) 입니다.", redisKey);
                return false;
            }

            return joinPoint.proceed();
        } catch(InterruptedException exception) {
            log.info(">>>>>>>>>>>>> Lock 경합 과정에서 에러가 발생했습니다. {}", redisKey, exception);
            throw new InterruptedException(exception.getMessage());
        } finally {
            try {
                redissonClientLock.unlock();
                log.info(">>>>>>>>>>>>> 처리를 완료하여 Lock({})을 해제 했습니다.", redisKey);
            } catch(IllegalMonitorStateException exception) {
                log.debug("Redisson lock already unLock {} {}", method.getName(), redisKey, exception);
            }
        }
    }
}
