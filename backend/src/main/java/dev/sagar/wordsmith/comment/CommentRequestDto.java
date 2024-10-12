package dev.sagar.wordsmith.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentRequestDto(
        @NotBlank(message = "Content cannot be blank")
        @Size(max = 1000, message = "Content cannot be more than 1000 characters")
        String content,

        @NotNull(message = "Post id cannot be null")
        Long postId,

        @NotNull(message = "User id cannot be null")
        Long userId
) {
}
