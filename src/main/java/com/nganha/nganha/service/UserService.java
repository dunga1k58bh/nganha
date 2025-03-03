package com.nganha.nganha.service;

import com.nganha.nganha.enums.Role;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }


    public User loadUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Method to check if user exists by username
    public boolean doesUserExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public User createUser(String username, String password, String email, Role role) {

        if (doesUserExist(username)) {
            throw new IllegalArgumentException("User with username " + username + " already exists.");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .role(role)
                .build();

        return saveUser(user);
    }
}