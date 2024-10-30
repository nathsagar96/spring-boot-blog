package com.codesmith.wordsmith.security;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling authentication-related requests.
 *
 * <p>This class provides endpoints for user registration and authentication by interacting with the
 * AuthenticationService.
 *
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication API", description = "APIs for authentication")
public class AuthenticationController {

  private final AuthenticationService authService;

  /**
   * Registers a new user in the system.
   *
   * @param request the registration request containing user details.
   * @return a string indicating the result of the registration process.
   */
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public String register(@Valid @RequestBody final RegisterRequestDto request) {
    return authService.register(request);
  }

  /**
   * Authenticates a user by validating their credentials.
   *
   * @param request the authentication request containing user credentials.
   * @return an {@link AuthenticationResponseDto} containing the authentication result, typically
   *     including a JWT token or error message.
   */
  @PostMapping("/authenticate")
  @ResponseStatus(HttpStatus.OK)
  public AuthenticationResponseDto authenticate(
      @Valid @RequestBody final AuthenticationRequestDto request) {
    return authService.authenticate(request);
  }
}
