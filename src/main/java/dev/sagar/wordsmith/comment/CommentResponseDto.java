package dev.sagar.wordsmith.comment;

import java.time.LocalDateTime;

public record CommentResponseDto(
    Long id, String content, LocalDateTime createdAt, Long postId, Long userId) {}
