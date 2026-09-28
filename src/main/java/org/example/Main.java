package org.example;

import org.example.controller.UserController;
import org.example.dto.User;

public class Main {

    public static void main(String[] args) {

        UserController controller = new UserController();

        // Signup
        User user = new User(
                1,
                "varshini@gmail.com",
                "Varshini@123",
                "USER"
        );
        boolean signupResult = controller.signup(user);

        System.out.println("Signup: " + signupResult);

        // Login
        boolean loginResult = controller.login(
                "varshini@gmail.com",
                "Varshini@123"
        );
        System.out.println("Login: " + loginResult);
    }
}