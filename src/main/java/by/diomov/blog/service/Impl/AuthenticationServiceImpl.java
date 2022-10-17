package by.diomov.blog.service.Impl;

import by.diomov.blog.exception.InvalidPasswordException;
import by.diomov.blog.exception.UserEmailExistsException;
import by.diomov.blog.exception.UserNameExistsException;
import by.diomov.blog.model.ERole;
import by.diomov.blog.model.User;
import by.diomov.blog.dto.security.request.LoginRequest;
import by.diomov.blog.dto.security.request.SignupRequest;
import by.diomov.blog.dto.security.response.JwtResponse;
import by.diomov.blog.repository.RoleRepository;
import by.diomov.blog.repository.UserRepository;
import by.diomov.blog.security.jwt.JwtUtils;
import by.diomov.blog.security.services.UserDetailsImpl;
import by.diomov.blog.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserNameExistsException(request.getUsername());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserEmailExistsException(request.getEmail());
        }

        userRepository.save(User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .roles(Set.of(roleRepository.findByName(ERole.ROLE_USER).get()))
                .build());
    }

    @Override
    public JwtResponse singin(LoginRequest request) {
        Authentication authentication;
        try {
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }catch (BadCredentialsException e){
            throw new InvalidPasswordException();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(toList());

        return JwtResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .token(jwt)
                .build();
    }
}