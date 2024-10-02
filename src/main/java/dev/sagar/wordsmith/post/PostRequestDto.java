package dev.sagar.wordsmith.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostRequestDto(
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 50, message = "Title cannot be more than 50 characters")
        String title,

        @NotBlank(message = "Content cannot be blank")
        @Size(max = 1000, message = "Content cannot be more than 1000 characters")
        String content,

        @NotNull(message = "User id cannot be null")
        Long userId,

        @NotNull(message = "Category id cannot be null")
        Integer categoryId
) {
}
