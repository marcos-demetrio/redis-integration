package com.example.redis.api;

import com.example.redis.domain.ProductDto;
import com.example.redis.domain.ProductService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("product")
public class ProductController {

  @Autowired private ProductService service;

  @GetMapping("{productId}")
  public Mono<ProductDto> getProduct(@PathVariable("productId") final UUID productId) {
    return this.service.findById(productId);
  }

  @PatchMapping("{id}")
  public Mono<Void> updateProduct(
      @PathVariable UUID productId, @RequestBody ProductDto productDto) {
    return this.service.update(productId, productDto);
  }
}
