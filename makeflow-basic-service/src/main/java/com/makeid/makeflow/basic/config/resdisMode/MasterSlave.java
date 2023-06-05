package com.makeid.makeflow.basic.config.resdisMode;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zx
 * @program redissonDemon
 * @description 主从模式
 * @create 2023-01-04
 */
@Component("master-slave")
public class MasterSlave implements RedissonStrategy {

    public static volatile RedissonClient masterSlaveClient = null;

    @Override
    public RedissonClient getRedissonClient(RedissonProperties redissonProperties) {

        if (masterSlaveClient != null) {
            return masterSlaveClient;
        }
        Config config = new Config();
        String[] adds = redissonProperties.getNodes().split(",");
        Set<String> nodeAddress = new HashSet<>();
        for (int i = 0; i < adds.length; i++) {
            StringBuilder stringBuilder = new StringBuilder("redis://");
            adds[i] = stringBuilder.append(adds[i]).toString();
            nodeAddress.add(adds[i]);
        }
        MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers().setMasterAddress(adds[0]);
        masterSlaveServersConfig.setSlaveAddresses(nodeAddress);
        if (StringUtils.hasText(redissonProperties.getPassword())) {
            masterSlaveServersConfig.setPassword(redissonProperties.getPassword());
        }
        if (StringUtils.hasText(redissonProperties.getDatabase())) {
            masterSlaveServersConfig.setDatabase(Integer.parseInt(redissonProperties.getDatabase()));
        }
        masterSlaveClient = Redisson.create(config);
        return masterSlaveClient;
    }

}
