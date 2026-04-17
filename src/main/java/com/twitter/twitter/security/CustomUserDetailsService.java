package com.twitter.twitter.security;

import com.twitter.twitter.entity.User;
import com.twitter.twitter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);


        if (userOptional.isPresent()) {

            User user = userOptional.get();

            return user;
        } else {

            throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}
