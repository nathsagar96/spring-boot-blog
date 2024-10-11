package dev.sagar.wordsmith.security;

import dev.sagar.wordsmith.exception.AlreadyExistException;
import dev.sagar.wordsmith.user.UserEntity;
import dev.sagar.wordsmith.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public String register(RegisterRequestDto request) {
        validateUserUniqueness(request);
        UserEntity userEntity = UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        UserEntity savedUserEntity = userRepository.save(userEntity);
        return "User registered successfully with id: " + savedUserEntity.getId();
    }

    private void validateUserUniqueness(RegisterRequestDto request) {
        String username = request.username();
        String email = request.email();

        if (userRepository.findByUsername(username).isPresent()) {
            throw new AlreadyExistException("User already exists with username: " + username);
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistException("User already exists with email: " + email);
        }
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        var auth =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(), request.password()));
        var user = ((User) auth.getPrincipal());
        var claims = new HashMap<String, Object>();
        claims.put("username", user.getUsername());
        var token = jwtService.generateToken(claims, user);
        return new AuthenticationResponseDto(token);
    }
}
