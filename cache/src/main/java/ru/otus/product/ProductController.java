package ru.otus.product;

import io.micrometer.core.annotation.Timed;
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
@Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService
    ) {
        this.productService = productService;
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product newProduct) {
        return ResponseEntity.ok(productService.save(newProduct));
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id) {
        try {
            productService.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok("Product deleted");
    }

    @Timed(value = "client.controller.requests", histogram = true, percentiles = { 0.5, 0.95, 0.99 })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productData, @PathVariable long id) {
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
}
