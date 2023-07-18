package com.makeid.makeflow.basic.config.resdisMode;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zx
 * @program redissonDemon
 * @description 集群模式
 * @create 2023-01-04
 */
@Component("cluster")
public class Cluster implements RedissonStrategy {

    public static volatile RedissonClient clusterClient = null;

    @Override
    public RedissonClient getRedissonClient(RedisProperties redissonProperties) {

        if (clusterClient != null) {
            return clusterClient;
        }
        String[] nodesList = redissonProperties.getNodes().split(",");
        for (int i = 0; i < nodesList.length; i++) {
            StringBuilder stringBuilder = new StringBuilder("redis://");
            nodesList[i] = stringBuilder.append(nodesList[i]).toString();
        }
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers().addNodeAddress(nodesList);
        if (StringUtils.hasText(redissonProperties.getPassword())) {
            clusterServersConfig.setPassword(redissonProperties.getPassword());
        }
        clusterClient = Redisson.create(config);
        return clusterClient;
    }

}
