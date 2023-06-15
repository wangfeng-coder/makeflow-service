package com.makeid.makeflow.basic.config.mongodb;

import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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

@Configuration
public class MongoConfig {

	public MongoConfig() {
		// TODO Auto-generated constructor stub
	}

	@Value("${mongo.dbname}")
	private String database;
	@Value("${mongo.replicaSet}")
	private String replicaSet;
	@Value("${mongo.connectionsPerHost}")
	private int connectionPerHost;
	@Value("${mongo.threadsAllowedToBlockForConnectionMultiplier}")
	private int threads;
	@Value("${mongo.connectTimeout}")
	private int connectTimeout;
	@Value("${mongo.maxWaitTime}")
	private int maxWaitTime;
	@Value("${mongo.socketTimeout}")
	private int socketTimeout;
	@Value("${mongo.username}")
	private String username;
	@Value("${mongo.password}")
	private String password;
	@Value("${mongo.authdb}")
	private String authdb;

//	@Bean("mongoClientPrimary")
//	public MongoClient mongoClient() {
	@Bean // 方法名即 ID
	public MongoClient mongoClientPrimary() {
		List<ServerAddress> serverAddresses = parseReplicaSet();
		MongoClientOptions options = mongoClientOptions();
		if (username == null || username.isEmpty()) {
			return new MongoClient(serverAddresses, options);
		} else {
			MongoCredential credential = MongoCredential.createCredential(username, authdb, password.toCharArray());
			return new MongoClient(serverAddresses, Arrays.asList(credential), options);
		}
	}

	private MongoClientOptions mongoClientOptions() {
		MongoClientOptions options = MongoClientOptions.builder()
				.connectionsPerHost(connectionPerHost)
				.threadsAllowedToBlockForConnectionMultiplier(threads)
				.connectTimeout(connectTimeout).maxWaitTime(maxWaitTime)
				.socketTimeout(socketTimeout).build();
		return options;
	}

	private List<ServerAddress> parseReplicaSet() {
		if (StringUtils.isBlank(replicaSet)) {
			throw new IllegalArgumentException("mongodb replicaset配置格式错误:"
					+ replicaSet);
		}

		String[] replicas = replicaSet.trim().split(",");
		List<ServerAddress> serverAddressList = new ArrayList<>(replicas.length);
		for (String replica : replicas) {
			String[] hostAndPort = replica.split(":");
			serverAddressList.add(new ServerAddress(hostAndPort[0], Integer
					.parseInt(hostAndPort[1])));
		}
		return serverAddressList;
	}

	@Bean
	public MongoDbFactory mongoDbFactory(MongoClient mongoClient) {
		MongoDbFactory dbFactory = new SimpleMongoDbFactory(mongoClient,
				database);
		return dbFactory;
	}

	@Bean
	public MongoMappingContext mappingContext() {
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
