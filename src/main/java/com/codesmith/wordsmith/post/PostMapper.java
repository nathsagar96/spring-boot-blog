package com.codesmith.wordsmith.post;

import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link Post} and {@link PostResponseDto} / {@link
 * PostRequestDto}.
 *
 * <p>This class provides methods to map data from the database entity to the response DTO used in
 * API responses, as well as to map data from the request DTO used in API requests to the database
 * entity.
 *
 * @version 1.0
 */
@Component
public class PostMapper {

  /**
   * Converts a {@link Post} to a {@link PostResponseDto}.
   *
   * @param post the post entity to convert.
   * @return a {@link PostResponseDto} containing the data from the post entity.
   */
  public PostResponseDto toDto(Post post) {
    return new PostResponseDto(
        post.getId(),
        post.getTitle(),
        post.getContent(),
        post.getCreatedAt(),
        post.getUpdatedAt(),
        post.getUserId(),
        post.getCategoryId());
  }

  /**
   * Converts a {@link PostRequestDto} to a {@link Post}.
   *
   * @param postRequestDto the request DTO containing the post data to convert.
   * @return a {@link Post} representing the data from the request DTO.
   */
  public Post toEntity(PostRequestDto postRequestDto) {
    return Post.builder()
        .title(postRequestDto.title())
        .content(postRequestDto.content())
        .userId(postRequestDto.userId())
        .categoryId(postRequestDto.categoryId())
        .build();
  }
}
