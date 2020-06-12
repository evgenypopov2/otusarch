package ru.otus.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.security.jwt.JwtProvider;
import ru.otus.user.entity.UserEntity;
import ru.otus.user.service.UserService;

import static org.springframework.util.StringUtils.hasText;

@RestController
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public UserController(
            UserService userService
            ,JwtProvider jwtProvider
    ) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> getProfile(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {

        String login = getLoginFromToken(auth);
        logger.info("GET /profile accessed by {}", login);
        if (login == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        logger.info("GET /profile: accessed by {}", login);
        UserEntity userEntity = userService.findByLogin(login);
        logger.info("GET /profile: user found: {}", userEntity);
        return userEntity != null ? ResponseEntity.ok().body(userEntity) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/profile")
    public ResponseEntity<UserEntity> getProfile(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestBody UpdateUserRequest userData) {

        String login = getLoginFromToken(auth);
        if (login == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        UserEntity userEntity = userService.findByLogin(login);
        return userEntity != null ? ResponseEntity.ok().body(updateUserData(userEntity, userData)) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> getUserEntity(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @PathVariable("id") int id) {

        if (!isCurrentUser(auth, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        UserEntity userEntity = userService.findById(id);
        return userEntity != null ? ResponseEntity.ok().body(userEntity) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserEntity> updateUserEntity(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestBody UpdateUserRequest userData,
            @PathVariable int id) {

        if (!isCurrentUser(auth, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        return ResponseEntity.ok().body(updateUserData(userService.findById(id), userData));
    }

    private UserEntity updateUserData(UserEntity userEntity, UpdateUserRequest userData) {
        if (userEntity != null) {
            if (userData.getLogin() != null)
                userEntity.setLogin(userData.getLogin());
            if (userData.getPassword() != null)
                userEntity.setPassword(userData.getPassword());
            if (userData.getFirstName() != null)
                userEntity.setFirstName(userData.getFirstName());
            if (userData.getLastName() != null)
                userEntity.setLastName(userData.getLastName());
            if (userData.getEmail() != null)
                userEntity.setEmail(userData.getEmail());
            if (userData.getPhone() != null)
                userEntity.setPhone(userData.getPhone());

            userEntity = userService.updateUser(userEntity);
        }
        return userEntity;
    }

    private boolean isCurrentUser(String auth, int userId) {
        String userLogin = getLoginFromToken(auth);
        if (userLogin != null) {
            UserEntity userEntity = userService.findByLogin(userLogin);
            return userEntity != null && userEntity.getId().equals(userId);
        }
        return false;
    }

    private String getLoginFromToken(String auth) {
        String token = getTokenFromHeader(auth);
        if (token != null) {
            return jwtProvider.getLoginFromToken(token);
        }
        return null;
    }

    private String getTokenFromHeader(String auth) {
        if (hasText(auth) && auth.startsWith("Bearer ")) {
            return auth.substring(7);
        }
        return null;
    }
}
