package dev.sagar.wordsmith.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRequestDto(
        @NotBlank(message = "Username cannot be blank")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @NotBlank(message = "Email cannot be blank")
        @Email
        String email,

        String firstName,
        String lastName,
        String phoneNumber,

        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        String address,
        String city,
        String state,
        String zipCode,
        String country,

        @NotBlank(message = "Role cannot be blank")
        @Pattern(regexp = "(?i)USER|ADMIN", message = "Role must be USER or ADMIN")
        String role,

        @Size(max = 500, message = "Bio cannot be more than 500 characters")
        String bio
) {
}
