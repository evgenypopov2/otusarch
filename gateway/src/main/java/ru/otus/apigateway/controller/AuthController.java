package ru.otus.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.apigateway.config.jwt.JwtProvider;
import ru.otus.apigateway.entity.UserEntity;
import ru.otus.apigateway.service.UserService;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserEntity u = new UserEntity();
        u.setPassword(registrationRequest.getPassword());
        u.setLogin(registrationRequest.getLogin());
        u.setFirstName(registrationRequest.getFirstName());
        u.setLastName(registrationRequest.getLastName());
        u.setEmail(registrationRequest.getEmail());
        u.setPhone(registrationRequest.getPhone());
        userService.saveUser(u);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        UserEntity userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        if (userEntity == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
