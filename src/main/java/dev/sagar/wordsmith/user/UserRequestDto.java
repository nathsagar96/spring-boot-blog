package dev.sagar.wordsmith.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRequestDto(
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @NotBlank(message = "Email cannot be blank")
        @Email
        String email,

        @NotBlank(message = "First name cannot be blank")
        @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
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
