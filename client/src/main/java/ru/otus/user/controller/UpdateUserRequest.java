package ru.otus.user.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
