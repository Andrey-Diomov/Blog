package by.diomov.blog.controller;

import by.diomov.blog.exception.EmptyBodyRequestException;
import by.diomov.blog.exception.WrongDataException;
import by.diomov.blog.dto.security.request.LoginRequest;
import by.diomov.blog.dto.security.request.SignupRequest;
import by.diomov.blog.dto.security.response.JwtResponse;
import by.diomov.blog.dto.security.response.MessageResponse;
import by.diomov.blog.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authService;

    @PostMapping("/signin")
    public JwtResponse login(@RequestBody @Valid LoginRequest request,
                             BindingResult bindingResult) {
        if (request == null) {
            throw new EmptyBodyRequestException();
        }
        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        return authService.singin(request);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> register(@RequestBody @Valid SignupRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        authService.signup(request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}