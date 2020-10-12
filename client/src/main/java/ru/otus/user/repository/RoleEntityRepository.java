package ru.otus.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.user.entity.RoleEntity;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByName(String name);
}
