package dev.sagar.wordsmith.comment;

import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public CommentEntity toEntity(CommentRequestDto commentRequestDto) {
        return new CommentEntity(
                commentRequestDto.content(),
                commentRequestDto.postId(),
                commentRequestDto.userId());
    }

    public CommentResponseDto toDto(CommentEntity commentEntity) {
        return new CommentResponseDto(
                commentEntity.getId(),
                commentEntity.getContent(),
                commentEntity.getCreatedAt(),
                commentEntity.getPostId(),
                commentEntity.getUserId());
    }
}
