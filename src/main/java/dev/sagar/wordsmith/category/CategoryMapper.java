package dev.sagar.wordsmith.category;

import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(CategoryRequestDto categoryRequestDto) {
        return new CategoryEntity(categoryRequestDto.title(), categoryRequestDto.description());
    }

    public CategoryResponseDto toDto(CategoryEntity categoryEntity) {
        return new CategoryResponseDto(categoryEntity.getId(), categoryEntity.getTitle(), categoryEntity.getDescription());
    }
}
