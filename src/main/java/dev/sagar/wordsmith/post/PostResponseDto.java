package dev.sagar.wordsmith.post;

import java.time.LocalDateTime;

public record PostResponseDto(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        Long userId,
        Integer categoryId
) {
}
