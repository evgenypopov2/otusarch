package ru.otus.apigateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.apigateway.entity.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByName(String name);
}
