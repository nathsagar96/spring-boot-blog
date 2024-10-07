package dev.sagar.wordsmith.post;

import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostResponseDto toDto(PostEntity postEntity) {
        return new PostResponseDto(postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getCreatedAt(),
                postEntity.getUpdatedAt(),
                postEntity.getUserId(),
                postEntity.getCategoryId());
    }

    public PostEntity toEntity(PostRequestDto postRequestDto) {
        return new PostEntity(postRequestDto.title(),
                postRequestDto.content(),
                postRequestDto.userId(),
                postRequestDto.categoryId());
    }
}
