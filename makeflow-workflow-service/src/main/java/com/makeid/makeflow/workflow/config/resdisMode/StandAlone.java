package com.makeid.makeflow.workflow.config.resdisMode;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * @author zx
 * @program redissonDemon
 * @description 单机模式
 * @create 2023-01-04
 */
@Component("stand-alone")
public class StandAlone implements RedissonStrategy {

    public static volatile RedissonClient standAloneClient = null;

    @Override
    public RedissonClient getRedissonClient(RedissonProperties redissonProperties) {
        if (standAloneClient != null) {
            return standAloneClient;
        }
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress("redis://" + redissonProperties.getHost() + ":" + redissonProperties.getPort());
        if (StringUtils.hasText(redissonProperties.getPassword())) {
            singleServerConfig.setPassword(redissonProperties.getPassword());
        }
        if (StringUtils.hasText(redissonProperties.getDatabase())) {
            singleServerConfig.setDatabase(Integer.parseInt(redissonProperties.getDatabase()));
        }
        standAloneClient = Redisson.create(config);
        return standAloneClient;
    }
}
