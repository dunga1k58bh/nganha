package com.nganha.nganha.command;

import com.nganha.nganha.enums.Role;
import com.nganha.nganha.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner {

    private final UserService userService;

    // Constructor injection of UserService
    public UserCommandLineRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Creating a default user (for example, a user with ROLE_USER)
        String username = "admin";
        String password = "admin123";
        String email = "admin@example.com";
        Role role = Role.ADMIN; // Or you can use Role.USER for a regular user

        // Check if the user already exists
        try {
            userService.createUser(username, password, email, role);
            System.out.println("User created successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
