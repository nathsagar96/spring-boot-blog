package dev.sagar.wordsmith.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseDto(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        String phoneNumber,
        LocalDate birthDate,
        String address,
        String city,
        String state,
        String zipCode,
        String country
) {
}