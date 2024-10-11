package dev.sagar.wordsmith.security;

public record RegisterRequestDto(
        String username,
        String email,
        String password
) {
}
