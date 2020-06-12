package ru.otus.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.user.entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByLogin(String login);
}
