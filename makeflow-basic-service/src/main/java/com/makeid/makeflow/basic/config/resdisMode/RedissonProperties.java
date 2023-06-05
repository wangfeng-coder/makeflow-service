package com.makeid.makeflow.basic.config.resdisMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zx
 * @program redissonDemon
 * @description redisson配置文件
 * @create 2023-01-04
 */
@Component
@ConfigurationProperties(prefix = "redisson.server")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedissonProperties {


    /**
     * redis主机地址
     */
    private String host;

    /**
     * PORT
     */
    private String port;

    /**
     * 连接类型，支持stand-alone-单机节点，sentinel-哨兵，cluster-集群，master-slave-主从
     */
    private String type;

    /**
     * redis 连接密码
     */
    private String password;

    /**
     * 选取那个数据库
     */
    private String database;

    /**
     * 节点信息
     */
    private String nodes;

    /**
     * 主节点
     */
    private String master;

    /**
     * 获取锁最长等待时间,单位秒
     */
    private Integer waitTime;

    /**
     * #锁过期最长时间，单位秒
     */
    private Integer leaseTime;

    public RedissonProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public RedissonProperties setDatabase(String database) {
        this.database = database;
        return this;
    }
}
