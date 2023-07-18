package com.makeid.makeflow.basic.config.resdisMode;

import org.redisson.api.RedissonClient;

/**
 * @author zx
 * @program redissonDemon
 * @description
 * @create 2023-01-04
 */
public interface RedissonStrategy {

    /**
     * @param redissonProperties
     * @return 获取客户端
     */
    public RedissonClient getRedissonClient(RedisProperties redissonProperties);

}
