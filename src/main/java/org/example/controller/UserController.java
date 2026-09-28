package org.example.controller;

import org.example.service.UserService;
import org.example.dto.User;

public class UserController {

    private UserService service;

    public UserController(){
        service = new UserService();
    }

    public boolean signup(User user) {
        return service.signup(user);
    }

    public boolean login(String email, String password) {
        return service.login(email, password);
    }

}
