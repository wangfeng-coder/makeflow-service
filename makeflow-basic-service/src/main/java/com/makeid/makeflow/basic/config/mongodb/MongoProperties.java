package com.makeid.makeflow.basic.config.mongodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "makeflow.data.mongo")
public class MongoProperties {

	private String dbname;

	private String replicaSet;

	private int connectionsPerHost;

	private int threadsAllowedToBlockForConnectionMultiplier;

	private int connectTimeout;

	private int maxWaitTime;

	private int socketTimeout;

	private String username;

	private String password;

	private String authdb;

//	@Bean("mongoClientPrimary")
//	public MongoClient mongoClient() {

}
