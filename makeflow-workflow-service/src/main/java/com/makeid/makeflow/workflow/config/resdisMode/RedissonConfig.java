package com.makeid.makeflow.workflow.config.resdisMode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;

/**
 * @author zx
 * @program redissonDemon
 * @description redisson 配置redis模式
 * @create 2023-01-04
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedissonConfig {

    private final RedissonProperties redissonProperties;

    private final Map<String, RedissonStrategy> redissonStrategyMap;

    @Bean
    public RedissonClient getRedisson() {
        RedissonStrategy redissonStrategy = redissonStrategyMap.get(redissonProperties.getType());
        if (Objects.isNull(redissonStrategy)) {
            log.error("请检查redisson配置文件");
            return null;
        }
        return redissonStrategy.getRedissonClient(redissonProperties);
    }

}
