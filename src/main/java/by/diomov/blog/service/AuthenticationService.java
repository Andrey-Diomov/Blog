package by.diomov.blog.service;

import by.diomov.blog.dto.security.request.LoginRequest;
import by.diomov.blog.dto.security.request.SignupRequest;
import by.diomov.blog.dto.security.response.JwtResponse;

public interface AuthenticationService {
    void signup(SignupRequest request);

    JwtResponse singin(LoginRequest request);
}
