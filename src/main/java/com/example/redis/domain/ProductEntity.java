package com.example.redis.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("product")
public class ProductEntity {

  @Id
  @Column("product_id")
  private UUID id;

  @Column("name")
  private String name;
}
