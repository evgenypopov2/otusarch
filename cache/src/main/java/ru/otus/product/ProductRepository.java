package ru.otus.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    //Product findByLogin(String login);
    //Product findById(UUID id);
    //List<Product> findByLastNameStartsWithIgnoreCase(String lastName);
}
