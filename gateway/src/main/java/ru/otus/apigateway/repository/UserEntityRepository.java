package ru.otus.apigateway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.apigateway.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByLogin(String login);
}
