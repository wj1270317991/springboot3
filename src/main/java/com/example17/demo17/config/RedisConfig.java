package com.example17.demo17.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * com.example17.demo17.config
 * ClassName: RedisConfig
 * Description:
 * Create by: wangjun
 * Date: 2024/1/15 17:57
 */
@Configuration
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;



    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s",redisProperties.getHost()+"",redisProperties.getPort()+"");
        config.useSingleServer().setAddress(redisUrl).setPassword(redisProperties.getPassword());
        config.useSingleServer().setDatabase(3);
        config.useSingleServer().setConnectionMinimumIdleSize(10);
        return Redisson.create(config);
    }




    @Bean
    public RBloomFilter<String>  bloomFilter(){
        // 定义一个布隆过滤器，指定布隆过滤器的名称
        RBloomFilter<String> bloomFilter = redissonClient().getBloomFilter("bloomTest");
        //定义布隆过滤器的大小，以及误差率
        bloomFilter.tryInit(100000L, 0.003);
        return bloomFilter;
    }


    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = initJacksonSerializer();
        // 设置value的序列化规则和 key的序列化规则
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }


    /**
     * 处理redis序列化问题
     * @return Jackson2JsonRedisSerializer
     */
    private Jackson2JsonRedisSerializer<Object> initJacksonSerializer() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //以下替代旧版本 om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        //bugFix Jackson2反序列化数据处理LocalDateTime类型时出错
        om.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        // java8 时间支持
        om.registerModule(new JavaTimeModule());
        return new Jackson2JsonRedisSerializer<>(om,Object.class);
    }




    /**
     * Redis cache configuration.
     *
     * @param redisTemplate the redis template
     * @return the redis cache configuration
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(RedisTemplate<Object, Object> redisTemplate, CacheProperties cacheProperties) {
        // 参见 spring.cache.redis
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 缓存的序列化问题
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(redisTemplate.getValueSerializer()));

        if (redisProperties.getTimeToLive() != null) {
            // 全局 TTL 时间
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            // key 前缀值
            redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            // 默认缓存null值 可以防止缓存穿透
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            // 不使用key前缀
            redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix();
        }
        return redisCacheConfiguration;
    }

    /**
     * Redis cache manager 个性化配置缓存过期时间.
     *
     * @return the redis cache manager builder customizer
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(RedisCacheConfiguration redisCacheConfiguration) {
        return builder -> builder.cacheDefaults(redisCacheConfiguration);
    }

}
