package com.twitter.twitter.service;

import com.twitter.twitter.dto.request.LoginRequest;
import com.twitter.twitter.dto.request.RegisterRequest;
import com.twitter.twitter.dto.response.AuthResponse;
import com.twitter.twitter.dto.response.UserResponse;
import com.twitter.twitter.entity.Role;
import com.twitter.twitter.entity.User;
import com.twitter.twitter.exception.DuplicateResourceException;
import com.twitter.twitter.repository.RoleRepository;
import com.twitter.twitter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        validateUniqueUser(request);

        Role defaultRole = findOrCreateDefaultRole();

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setBio(request.bio());
        user.setRoles(List.of(defaultRole));

        User savedUser = userRepository.save(user);

        return new AuthResponse(toUserResponse(savedUser));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException(
                        "User not found: " + request.username()));

        return new AuthResponse(toUserResponse(user));
    }

    private void validateUniqueUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new DuplicateResourceException("Username is already in use");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email is already in use");
        }
    }

    private Role findOrCreateDefaultRole() {
        return roleRepository.findByName(DEFAULT_ROLE)
                .orElseGet(() -> roleRepository.save(createDefaultRole()));
    }

    private Role createDefaultRole() {
        Role role = new Role();
        role.setName(DEFAULT_ROLE);
        return role;
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getTweets() == null ? 0 : user.getTweets().size()
        );
    }
}
