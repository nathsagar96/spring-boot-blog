package dev.sagar.wordsmith.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDto(
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 50, message = "Title cannot be more than 50 characters")
        String title,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 100, message = "Description cannot be more than 100 characters")
        String description
) {
}
