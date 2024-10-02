package dev.sagar.wordsmith.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record UserRequestDto(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Password cannot be blank")
        String password,

        @NotBlank(message = "Email cannot be blank")
        @Email
        String email,

        @NotBlank(message = "First name cannot be blank")
        String firstName,
        String lastName,

        @NotBlank(message = "Phone number cannot be blank")
        String phoneNumber,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        String address,
        String city,
        String state,
        String zipCode,
        String country
) {
}
