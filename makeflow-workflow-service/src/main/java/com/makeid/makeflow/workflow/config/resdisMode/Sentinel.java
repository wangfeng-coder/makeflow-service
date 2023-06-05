package com.makeid.makeflow.workflow.config.resdisMode;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zx
 * @program redissonDemon
 * @description 哨兵模式
 * @create 2023-01-04
 */
@Component("sentinel")
public class Sentinel implements RedissonStrategy {

    public static volatile RedissonClient sentinelClient = null;

    @Override
    public RedissonClient getRedissonClient(RedissonProperties redissonProperties) {

        if (sentinelClient != null) {
            return sentinelClient;
        }

        Config config = new Config();
        String[] adds = redissonProperties.getNodes().split(",");
        for (int i = 0; i < adds.length; i++) {
            StringBuilder stringBuilder = new StringBuilder("redis://");
            adds[i] = stringBuilder.append(adds[i]).toString();
        }
        SentinelServersConfig serverConfig = config.useSentinelServers()
                .addSentinelAddress(adds)
                .setMasterName(redissonProperties.getMaster());
        if (StringUtils.hasText(redissonProperties.getPassword())) {
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        if (StringUtils.hasText(redissonProperties.getDatabase())) {
            serverConfig.setDatabase(Integer.parseInt(redissonProperties.getDatabase()));
        }
        sentinelClient = Redisson.create(config);
        return sentinelClient;
    }


}
