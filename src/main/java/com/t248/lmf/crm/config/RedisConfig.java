package com.t248.lmf.crm.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * 自定义缓存读写机制
 */
@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                //格式化Key字符串
                StringBuilder sb = new StringBuilder();
                //追加类名
                sb.append(target.getClass().getName());
                //追加方法名
                sb.append(method.getName());
                //遍历并追加
                for (Object obj:params){
                    sb.append(obj.toString());
                }
                System.out.println("调用Redis缓存Key："+sb.toString());
                return sb.toString();
            }
        };
    }

    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory){
        RedisCacheConfiguration cacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofDays(1))
                        .disableCachingNullValues()
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new  GenericJackson2JsonRedisSerializer()));
        return RedisCacheManager.builder(factory).cacheDefaults(cacheConfiguration).build();
    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//        template.setConnectionFactory(factory);
//        //key序列化方式
//        template.setKeySerializer(redisSerializer);
//        //value序列化
//        template.setValueSerializer(new JdkSerializationRedisSerializer());
//        //value hashmap序列化
//        template.setHashValueSerializer(redisSerializer);
//        template.setHashKeySerializer(redisSerializer);
//        return template;
//    }

//    @Bean
//    public RedisTemplate<String, String> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(jedisConnectionFactory);
//        return redisTemplate;
//    }


//    @Bean
//    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
//        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
//
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//
//        MyObjectMapper objectMapper = new MyObjectMapper();
//
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//
//        redisTemplate.afterPropertiesSet();
//
//        return redisTemplate;
//
//    }

    /**
     * 采用RedisCacheManager作为缓存管理器
     * @param factory
     * @return
     */
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory){
//        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
//
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//
//        MyObjectMapper objectMapper = new MyObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        //配置序列化
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//
//        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith
//                (RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).
//                serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
//        RedisCacheManager cacheManager = RedisCacheManager.builder(factory).
//                cacheDefaults(redisCacheConfiguration.entryTtl(Duration.ofMinutes(5))).build();
//        return cacheManager;
//
//    }
//    public class MyObjectMapper extends ObjectMapper {
//
//        private static final long serialVersionUID=1L;
//
//        public MyObjectMapper(){
//            super();
//
//            this.configure(MapperFeature.USE_ANNOTATIONS,false);
//
//            this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//            this.enableDefaultTyping(DefaultTyping.NON_FINAL, JsonT
//            ypeInfo.As.PROPERTY);
//
//            this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//
//            this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
//        }
//
//    }

}
