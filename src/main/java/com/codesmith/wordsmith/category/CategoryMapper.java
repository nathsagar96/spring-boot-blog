package com.codesmith.wordsmith.category;

import org.springframework.stereotype.Component;

/**
 * A mapper component that handles the conversion between {@link CategoryRequestDto}, {@link
 * Category}, and {@link CategoryResponseDto}.
 *
 * <p>This class provides methods to map data between the entity and DTO representations of a
 * category, facilitating the separation between different layers of the application.
 *
 * <p>Annotated with {@link Component} to allow Spring to detect it for dependency injection.
 *
 * @version 1.0
 */
@Component
public class CategoryMapper {

  /**
   * Converts a {@link CategoryRequestDto} to a {@link Category}.
   *
   * @param categoryRequestDto the DTO containing category data to convert.
   * @return a {@link Category} constructed from the provided {@link CategoryRequestDto}.
   */
  public Category toEntity(CategoryRequestDto categoryRequestDto) {
    return Category.builder()
        .title(categoryRequestDto.title())
        .description(categoryRequestDto.description())
        .build();
  }

  /**
   * Converts a {@link Category} to a {@link CategoryResponseDto}.
   *
   * @param category the entity containing category data to convert.
   * @return a {@link CategoryResponseDto} constructed from the provided {@link Category}.
   */
  public CategoryResponseDto toDto(Category category) {
    return new CategoryResponseDto(
        category.getId(), category.getTitle(), category.getDescription());
  }
}
