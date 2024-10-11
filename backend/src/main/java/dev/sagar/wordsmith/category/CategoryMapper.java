package dev.sagar.wordsmith.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(CategoryRequestDto categoryRequestDto) {
        return CategoryEntity.builder()
                .title(categoryRequestDto.title())
                .description(categoryRequestDto.description())
                .build();
    }

    public CategoryResponseDto toDto(CategoryEntity categoryEntity) {
        return new CategoryResponseDto(categoryEntity.getId(), categoryEntity.getTitle(), categoryEntity.getDescription());
    }
}
