package com.example.redis.domain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
  public ProductDto toDto(ProductEntity entity) {
    return ProductDto.builder().id(entity.getId()).name(entity.getName()).build();
  }

  public ProductEntity toEntity(ProductDto dto) {
    return ProductEntity.builder().id(dto.getId()).name(dto.getName()).build();
  }
}
