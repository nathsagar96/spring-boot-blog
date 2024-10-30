package com.codesmith.wordsmith.post;

import java.time.LocalDateTime;

public record PostResponseDto(
    Long id,
    String title,
    String content,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Long userId,
    Integer categoryId) {}
