package com.nganha.nganha.service;

import com.nganha.nganha.enums.Role;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }


    public Optional<User> getUserById(Long id) {
        if (id == null || id == 0) {
            return Optional.empty(); // Return empty if id is null or 0
        }
        return userRepository.findById(id);
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