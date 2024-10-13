package dev.sagar.wordsmith.post;

import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link PostEntity} and {@link PostResponseDto} / {@link PostRequestDto}.
 *
 * <p>This class provides methods to map data from the database entity to the response DTO used in API responses,
 * as well as to map data from the request DTO used in API requests to the database entity.</p>
 *
 * @version 1.0
 */
@Component
public class PostMapper {

    /**
     * Converts a {@link PostEntity} to a {@link PostResponseDto}.
     *
     * @param postEntity the post entity to convert.
     * @return a {@link PostResponseDto} containing the data from the post entity.
     */
    public PostResponseDto toDto(PostEntity postEntity) {
        return new PostResponseDto(postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getCreatedAt(),
                postEntity.getUpdatedAt(),
                postEntity.getUserId(),
                postEntity.getCategoryId());
    }

    /**
     * Converts a {@link PostRequestDto} to a {@link PostEntity}.
     *
     * @param postRequestDto the request DTO containing the post data to convert.
     * @return a {@link PostEntity} representing the data from the request DTO.
     */
    public PostEntity toEntity(PostRequestDto postRequestDto) {
        return PostEntity.builder()
                .title(postRequestDto.title())
                .content(postRequestDto.content())
                .userId(postRequestDto.userId())
                .categoryId(postRequestDto.categoryId())
                .build();
    }
}
