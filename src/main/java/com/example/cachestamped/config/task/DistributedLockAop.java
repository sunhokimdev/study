package com.example.cachestamped.config.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAop {
    private static final String GET_LOCK = "SELECT GET_LOCK(?, ?)";
    private static final String RELEASE_LOCK = "SELECT RELEASE_LOCK(?)";
    private static final String EXCEPTION_MESSAGE = "LOCK 을 수행하는 중에 오류가 발생하였습니다.";

    private final AopForTransaction aopForTransaction;
    private final DataSource dataSource;

    @Around("@annotation(com.example.cachestamped.config.task.DistributedLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String key = (String) CustomSpringELParser.getDynamicValue(methodSignature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());

        try(Connection connection = dataSource.getConnection()) {
            try {
                getLock(connection, key, 5);
                return aopForTransaction.proceed(joinPoint);
            } finally {
                releaseLock(connection, key);
            }
        } catch(SQLException | RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void getLock(Connection connection, String userLockName, int timeoutSeconds) throws SQLException {
        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCK)) {
            preparedStatement.setString(1, userLockName);
            preparedStatement.setLong(2, timeoutSeconds);
            checkResultSet(userLockName, preparedStatement, "GetLock");
        }
    }

    private void releaseLock(Connection connection, String userLockName) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(RELEASE_LOCK)) {
            preparedStatement.setString(1, userLockName);

            checkResultSet(userLockName, preparedStatement, "ReleaseLock_");
        }
    }

    private void checkResultSet(String userLockName, PreparedStatement preparedStatement, String type) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                log.error("USER LEVEL LOCK 쿼리 결과 값이 없습니다. type: {}, userLockName: {}, connection: {}", type, userLockName, preparedStatement.getConnection());
                throw new RuntimeException(EXCEPTION_MESSAGE);
            }
            int result = resultSet.getInt(1);
            if (result != 1) {
                log.error("USER LEVEL LOCK 쿼리 결과 값이 1이 아닙니다. type: {}, result: {} userLockName: {}, connection: {}", type, result, userLockName, preparedStatement.getConnection());
                throw new RuntimeException(EXCEPTION_MESSAGE);
            }
        }
    }
}
