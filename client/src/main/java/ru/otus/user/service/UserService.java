package ru.otus.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.user.entity.RoleEntity;
import ru.otus.user.entity.UserEntity;
import ru.otus.user.repository.RoleEntityRepository;
import ru.otus.user.repository.UserEntityRepository;

@Service
public class UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private RoleEntityRepository roleEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    private final UserEntityRepository userEntityRepository;
//    private final RoleEntityRepository roleEntityRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserService(
//        UserEntityRepository userEntityRepository
//        ,RoleEntityRepository roleEntityRepository
//        ,PasswordEncoder passwordEncoder
//    ) {
//        this.userEntityRepository = userEntityRepository;
//        this.roleEntityRepository = roleEntityRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    public UserEntity saveUser(UserEntity userEntity) {
        RoleEntity userRole = roleEntityRepository.findByName("ROLE_USER");
        userEntity.setRole(userRole);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
    }

    public UserEntity updateUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityRepository.save(userEntity);
    }

    public UserEntity findById(int id) {
        return userEntityRepository.findById(id).orElse(null);
    }

    public UserEntity findByLogin(String login) {
        return userEntityRepository.findByLogin(login);
    }

    public UserEntity findByLoginAndPassword(String login, String password) {
        UserEntity userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
}
