package dev.sagar.wordsmith.security;

import dev.sagar.wordsmith.exception.AlreadyExistException;
import dev.sagar.wordsmith.user.UserEntity;
import dev.sagar.wordsmith.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Service class for handling authentication and user registration.
 *
 * <p>This class provides methods to register new users and authenticate existing users
 * using username and password. It also handles user uniqueness validation.</p>
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    /**
     * Registers a new user in the system.
     *
     * <p>This method validates the uniqueness of the username and email,
     * encodes the password, and saves the new user entity to the database.</p>
     *
     * @param request the registration request containing user details.
     * @return a success message indicating the user's registration status.
     * @throws AlreadyExistException if a user with the same username or email already exists.
     */
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

    /**
     * Validates the uniqueness of the username and email during registration.
     *
     * <p>This method checks if the username or email already exists in the database,
     * throwing an exception if either is found.</p>
     *
     * @param request the registration request containing the username and email to validate.
     * @throws AlreadyExistException if the username or email already exists in the system.
     */
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

    /**
     * Authenticates a user by validating their credentials.
     *
     * <p>This method attempts to authenticate the user with the provided username and password,
     * generating a JWT token upon successful authentication.</p>
     *
     * @param request the authentication request containing user credentials.
     * @return an AuthenticationResponseDto containing the generated JWT token.
     * @throws BadCredentialsException if the username or password is incorrect.
     */
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
