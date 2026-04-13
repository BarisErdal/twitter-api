package com.twitter.twitter.service;


import com.twitter.twitter.dto.request.LoginRequest;
import com.twitter.twitter.dto.request.RegisterRequest;
import com.twitter.twitter.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
