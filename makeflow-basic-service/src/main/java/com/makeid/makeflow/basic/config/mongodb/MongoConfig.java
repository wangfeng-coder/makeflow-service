package com.makeid.makeflow.basic.config.mongodb;

import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author feng_wf
 * @program makeflow-service
 * @description
 * @create 2023-07-11
 */
@Configuration
@ConditionalOnProperty(prefix = "makeflow.data",name = "dataType",havingValue = "mongodb")
@EnableConfigurationProperties(MongoProperties.class)
public class MongoConfig {


    @Bean // 方法名即 ID
    public MongoClient mongoClientPrimary(MongoProperties mongoProperties) {
        List<ServerAddress> serverAddresses = parseReplicaSet(mongoProperties);
        MongoClientOptions options = mongoClientOptions(mongoProperties);
        if (mongoProperties.getUsername() == null || mongoProperties.getUsername().isEmpty()) {
            return new MongoClient(serverAddresses, options);
        } else {
            MongoCredential credential = MongoCredential.createCredential(mongoProperties.getUsername(), mongoProperties.getAuthdb(), mongoProperties.getPassword().toCharArray());
            return new MongoClient(serverAddresses, Arrays.asList(credential), options);
        }
    }

    private MongoClientOptions mongoClientOptions(MongoProperties mongoProperties) {
        MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(mongoProperties.getConnectionsPerHost())
                .threadsAllowedToBlockForConnectionMultiplier(mongoProperties.getThreadsAllowedToBlockForConnectionMultiplier())
                .connectTimeout(mongoProperties.getConnectTimeout()).maxWaitTime(mongoProperties.getMaxWaitTime())
                .socketTimeout(mongoProperties.getSocketTimeout()).build();
        return options;
    }

    private List<ServerAddress> parseReplicaSet(MongoProperties mongoProperties) {
        if (StringUtils.isBlank(mongoProperties.getReplicaSet())) {
            throw new IllegalArgumentException("mongodb replicaset配置格式错误:"
                    + mongoProperties.getReplicaSet());
        }

        String[] replicas = mongoProperties.getReplicaSet().trim().split(",");
        List<ServerAddress> serverAddressList = new ArrayList<>(replicas.length);
        for (String replica : replicas) {
            String[] hostAndPort = replica.split(":");
            serverAddressList.add(new ServerAddress(hostAndPort[0], Integer
                    .parseInt(hostAndPort[1])));
        }
        return serverAddressList;
    }

    @Bean
    public MongoDbFactory mongoDbFactory(MongoClient mongoClient,MongoProperties mongoProperties) {
        MongoDbFactory dbFactory = new SimpleMongoDbFactory(mongoClient,
                 mongoProperties.getDbname());
        return dbFactory;
    }

    @Bean
    public MongoMappingContext mappingContext() {
        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        //新版本不推荐自动创建索引了
        mongoMappingContext.setAutoIndexCreation(false);
        return new MongoMappingContext();
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDbFactory mongoDbFactory, MongoMappingContext mappingContext) {
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(
                new DefaultDbRefResolver(mongoDbFactory), mappingContext);
        return mappingMongoConverter;
    }

    @Bean
    public DefaultMongoTypeMapper defaultMongoTypeMapper() {
        DefaultMongoTypeMapper defaultMongoTypeMapper = new DefaultMongoTypeMapper();
        return defaultMongoTypeMapper;
    }

    //	@Bean("mongoTemplate")
    @Bean // 方法名即 ID
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory,
                                       MappingMongoConverter mappingMongoConverter) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory,
                mappingMongoConverter);
        mongoTemplate.setReadPreference(ReadPreference.secondaryPreferred());
        return mongoTemplate;
    }

}
