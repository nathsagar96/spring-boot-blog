package com.codesmith.wordsmith.comment;

import org.springframework.stereotype.Component;

/**
 * A mapper component that handles the conversion between {@link CommentRequestDto}, {@link
 * Comment}, and {@link CommentResponseDto}.
 *
 * <p>This class provides methods to map data between the entity and DTO representations of a
 * comment, facilitating the separation of concerns between different layers of the application.
 *
 * <p>Annotated with {@link Component} to allow Spring to detect it for dependency injection.
 *
 * @version 1.0
 * @see Comment
 * @see CommentRequestDto
 * @see CommentResponseDto
 */
@Component
public class CommentMapper {

  /**
   * Converts a {@link CommentRequestDto} to a {@link Comment}.
   *
   * @param commentRequestDto the DTO containing comment data to convert.
   * @return a {@link Comment} constructed from the provided {@link CommentRequestDto}.
   */
  public Comment toEntity(CommentRequestDto commentRequestDto) {
    return Comment.builder()
        .content(commentRequestDto.content())
        .postId(commentRequestDto.postId())
        .userId(commentRequestDto.userId())
        .build();
  }

  /**
   * Converts a {@link Comment} to a {@link CommentResponseDto}.
   *
   * @param comment the entity containing comment data to convert.
   * @return a {@link CommentResponseDto} constructed from the provided {@link Comment}.
   */
  public CommentResponseDto toDto(Comment comment) {
    return new CommentResponseDto(
        comment.getId(),
        comment.getContent(),
        comment.getCreatedAt(),
        comment.getPostId(),
        comment.getUserId());
  }
}
