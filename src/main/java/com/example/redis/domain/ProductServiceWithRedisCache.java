package com.example.redis.domain;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@ConditionalOnProperty(name = "app.datasource.redis.enabled", havingValue = "true")
public class ProductServiceWithRedisCache extends ProductServiceWithNoCache {
  private static final String KEY = "product";

  @Autowired private ReactiveHashOperations<String, UUID, ProductDto> hashOperations;

  @Override
  public Mono<ProductDto> findById(final UUID productId) {
    return this.hashOperations
        .get(KEY, productId)
        .switchIfEmpty(this.getFromDatabaseAndCache(productId));
  }

  @Override
  public Mono<Void> update(final UUID productId, final ProductDto productDto) {
    return super.update(productId, productDto)
        .then(this.hashOperations.remove(KEY, productId))
        .then();
  }

  public Mono<ProductDto> getFromDatabaseAndCache(final UUID productId) {
    return super.findById(productId)
        .flatMap(
            productDto ->
                this.hashOperations.put(KEY, productId, productDto).thenReturn(productDto));
  }
}
