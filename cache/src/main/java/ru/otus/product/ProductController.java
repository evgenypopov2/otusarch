package ru.otus.product;

import org.ehcache.core.spi.service.StatisticsService;
import org.ehcache.core.statistics.DefaultStatisticsService;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;
    private final CacheManager cacheManager;
    private StatisticsService statisticsService;

    public ProductController(
            ProductService productService
            ,CacheManager cacheManager
    ) {
        this.productService = productService;
        this.cacheManager = cacheManager;
        statisticsService = new DefaultStatisticsService();
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) throws InterruptedException {
        return ResponseEntity.ok(productService.save(newProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) throws InterruptedException {
        Optional<Product> product = productService.findById(id);
        return product.isPresent() ? ResponseEntity.ok(product.get()) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id) {
        try {
            productService.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok("Product deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productData, @PathVariable long id) throws InterruptedException {
        return productService.findById(id)
                .map(product -> {
                    product.setName(productData.getName());
                    product.setCategory(productData.getCategory());
                    product.setPrice(productData.getPrice());
                    product = productService.save(product);
                    return ResponseEntity.ok(product);
                })
                .orElseGet(() -> {
                    productData.setId(id);
                    return ResponseEntity.ok(productService.save(productData));
                });
    }

    @GetMapping("/cache/statistics")
    public ResponseEntity<List<CacheStatisticsDto>> getCacheState() {
        return ResponseEntity.ok(
                cacheManager.getCacheNames().stream()
                        .map(cacheName -> CacheStatisticsDto.fromCacheStatistics(statisticsService.getCacheStatistics(cacheName)))
                        .collect(Collectors.toList()));
    }
}
