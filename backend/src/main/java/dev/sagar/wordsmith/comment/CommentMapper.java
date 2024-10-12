package dev.sagar.wordsmith.comment;

import org.springframework.stereotype.Component;

/**
 * A mapper component that handles the conversion between
 * {@link CommentRequestDto}, {@link CommentEntity}, and {@link CommentResponseDto}.
 *
 * <p>This class provides methods to map data between the entity and DTO representations
 * of a comment, facilitating the separation of concerns between different layers
 * of the application.</p>
 *
 * <p>Annotated with {@link Component} to allow Spring to detect it for dependency injection.</p>
 *
 * @version 1.0
 * @see CommentEntity
 * @see CommentRequestDto
 * @see CommentResponseDto
 */
@Component
public class CommentMapper {

    /**
     * Converts a {@link CommentRequestDto} to a {@link CommentEntity}.
     *
     * @param commentRequestDto the DTO containing comment data to convert.
     * @return a {@link CommentEntity} constructed from the provided {@link CommentRequestDto}.
     */
    public CommentEntity toEntity(CommentRequestDto commentRequestDto) {
        return CommentEntity.builder()
                .content(commentRequestDto.content())
                .postId(commentRequestDto.postId())
                .userId(commentRequestDto.userId())
                .build();
    }

    /**
     * Converts a {@link CommentEntity} to a {@link CommentResponseDto}.
     *
     * @param commentEntity the entity containing comment data to convert.
     * @return a {@link CommentResponseDto} constructed from the provided {@link CommentEntity}.
     */
    public CommentResponseDto toDto(CommentEntity commentEntity) {
        return new CommentResponseDto(
                commentEntity.getId(),
                commentEntity.getContent(),
                commentEntity.getCreatedAt(),
                commentEntity.getPostId(),
                commentEntity.getUserId()
        );
    }
}
