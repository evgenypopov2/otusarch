package ru.otus.apigateway.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
