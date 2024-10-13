package dev.sagar.wordsmith.category;

import org.springframework.stereotype.Component;

/**
 * A mapper component that handles the conversion between
 * {@link CategoryRequestDto}, {@link CategoryEntity}, and {@link CategoryResponseDto}.
 *
 * <p>This class provides methods to map data between the entity and DTO representations
 * of a category, facilitating the separation between different layers of the application.</p>
 *
 * <p>Annotated with {@link Component} to allow Spring to detect it for dependency injection.</p>
 *
 * @version 1.0
 */
@Component
public class CategoryMapper {

    /**
     * Converts a {@link CategoryRequestDto} to a {@link CategoryEntity}.
     *
     * @param categoryRequestDto the DTO containing category data to convert.
     * @return a {@link CategoryEntity} constructed from the provided {@link CategoryRequestDto}.
     */
    public CategoryEntity toEntity(CategoryRequestDto categoryRequestDto) {
        return CategoryEntity.builder()
                .title(categoryRequestDto.title())
                .description(categoryRequestDto.description())
                .build();
    }

    /**
     * Converts a {@link CategoryEntity} to a {@link CategoryResponseDto}.
     *
     * @param categoryEntity the entity containing category data to convert.
     * @return a {@link CategoryResponseDto} constructed from the provided {@link CategoryEntity}.
     */
    public CategoryResponseDto toDto(CategoryEntity categoryEntity) {
        return new CategoryResponseDto(
                categoryEntity.getId(),
                categoryEntity.getTitle(),
                categoryEntity.getDescription()
        );
    }
}
