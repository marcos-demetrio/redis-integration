package com.example.redis.domain;

import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ProductService {
  Mono<ProductDto> findById(final UUID productId);

  Mono<Void> update(final UUID productId, ProductDto productDto);
}
