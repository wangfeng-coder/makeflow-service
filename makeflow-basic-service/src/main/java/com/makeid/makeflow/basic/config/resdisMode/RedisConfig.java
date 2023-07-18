package com.makeid.makeflow.basic.config.resdisMode;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.net.UnknownHostException;
import java.nio.charset.Charset;
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
@EnableConfigurationProperties({RedisProperties.class})
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redissonProperties;

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

    @Bean
    @ConditionalOnProperty(prefix = "makeflow.redis.server",value = "type",havingValue = "stand-alone")
    public RedisStandaloneConfiguration redisStandaloneConfiguration(RedisProperties redisProperties) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(Integer.parseInt(redisProperties.getDatabase()));
        redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisProperties.getPort()));
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        return redisStandaloneConfiguration;
    }

    @Bean
    @ConditionalOnProperty(prefix = "makeflow.redis.server",value = "type",havingValue = "sentinel")
    public RedisSentinelConfiguration redisSentinelConfiguration(RedisProperties redisProperties) {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration.setDatabase(Integer.parseInt(redisProperties.getDatabase()));
        String nodes = redisProperties.getNodes();
        String[] split = nodes.split(",");
        for (String s : split) {
            s = s.replaceAll("\\s", "").replaceAll("\n", "");
            int i = s.indexOf(":");
            String ip = s.substring(0,i);
            String port = s.substring(i+1,s.length());
            redisSentinelConfiguration.addSentinel(new RedisNode(ip,Integer.parseInt(port)));
        }
        redisSentinelConfiguration.setMaster(redisProperties.getMaster());
        redisSentinelConfiguration.setSentinelPassword(redisProperties.getPassword());
        return redisSentinelConfiguration;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisConfiguration redisConfiguration){
        return new LettuceConnectionFactory(redisConfiguration);
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        FastJson2JsonRedisSerializer<Object> objectFastJson2JsonRedisSerializer = new FastJson2JsonRedisSerializer<Object>(Object.class);
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(objectFastJson2JsonRedisSerializer);
        template.setValueSerializer(objectFastJson2JsonRedisSerializer);
        template.setHashKeySerializer(objectFastJson2JsonRedisSerializer);
        template.setHashValueSerializer(objectFastJson2JsonRedisSerializer);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }



}
class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;


    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return JSON.parseObject(str, clazz, JSONReader.Feature.SupportAutoType);
    }
}
