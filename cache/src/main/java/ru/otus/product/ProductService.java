package ru.otus.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //@Cacheable
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Cacheable(value = "productCache", key = "#id")
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @CachePut(value = "productCache", key = "#product.id")
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = "productCache", key = "#id")
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }
}
