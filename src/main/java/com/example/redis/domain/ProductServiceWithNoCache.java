package com.example.redis.domain;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@ConditionalOnProperty(name = "app.datasource.redis.enabled", havingValue = "false")
public class ProductServiceWithNoCache implements ProductService {

  @Autowired private ProductRepository repository;

  @Override
  public Mono<ProductDto> findById(final UUID productId) {
    return this.repository.findById(productId).map(ProductMapper::toDto);
  }

  @Override
  public Mono<Void> update(final UUID productId, final ProductDto productDto) {
    return this.findById(productId)
        .doOnNext(dto -> dto.setName(productDto.getName()))
        .map(ProductMapper::toEntity)
        .flatMap(repository::save)
        .then();
  }
}
