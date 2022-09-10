package com.example.redis.config;

import com.example.redis.domain.ProductDto;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@ConditionalOnProperty(name = "app.datasource.redis.enabled", havingValue = "true")
public class RedisConfig {
  @Value("${app.datasource.redis.host}")
  private String host;

  @Value("${app.datasource.redis.port}")
  private int port;

  @Bean
  public ReactiveRedisConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(host, port);
  }

  @Bean
  public ReactiveHashOperations<String, UUID, ProductDto> hashOperations(
      ReactiveRedisConnectionFactory redisConnectionFactory) {

    var template =
        new ReactiveRedisTemplate<>(
            redisConnectionFactory,
            RedisSerializationContext.<String, ProductDto>newSerializationContext(
                    new StringRedisSerializer())
                .hashKey(new GenericToStringSerializer<>(UUID.class))
                .hashValue(new Jackson2JsonRedisSerializer<>(ProductDto.class))
                .build());

    return template.opsForHash();
  }
}
