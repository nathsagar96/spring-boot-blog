package dev.sagar.wordsmith.category;

import dev.sagar.wordsmith.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing category-related operations.
 *
 * <p>This class provides methods to handle business logic for creating, retrieving,
 * updating, and deleting categories. It interacts with the data persistence layer
 * through {@link CategoryRepository} and uses {@link CategoryMapper} to map
 * between entities and DTOs.</p>
 *
 * <p>Annotated with {@link Service} to denote it as a service component in the Spring context,
 * allowing for dependency injection.</p>
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    /**
     * Retrieves a list of all categories.
     *
     * @return a list of {@link CategoryResponseDto} representing all categories.
     */
    @Cacheable(value = "categories")
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a specific category by its ID.
     *
     * @param categoryId the ID of the category to retrieve.
     * @return a {@link CategoryResponseDto} representing the category with the given ID.
     * @throws ResourceNotFoundException if no category is found with the provided ID.
     */
    @Cacheable(value = "categories", key = "#categoryId")
    public CategoryResponseDto getCategoryById(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        return categoryMapper.toDto(categoryEntity);
    }

    /**
     * Creates a new category based on the provided data.
     *
     * @param categoryRequestDto the DTO containing the new category data.
     * @return a {@link CategoryResponseDto} representing the created category.
     */
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryRequestDto);
        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.toDto(savedCategoryEntity);
    }

    /**
     * Updates an existing category with the provided data.
     *
     * @param categoryId         the ID of the category to update.
     * @param categoryRequestDto the DTO containing the updated category data.
     * @return a {@link CategoryResponseDto} representing the updated category.
     * @throws ResourceNotFoundException if no category is found with the provided ID.
     */
    @CachePut(value = "categories", key = "#categoryId")
    public CategoryResponseDto updateCategory(Integer categoryId, CategoryRequestDto categoryRequestDto) {
        CategoryEntity categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        categoryEntity.setTitle(categoryRequestDto.title());
        categoryEntity.setDescription(categoryRequestDto.description());

        CategoryEntity savedCategoryEntity = categoryRepository.save(categoryEntity);
        return categoryMapper.toDto(savedCategoryEntity);
    }

    /**
     * Deletes a specific category by its ID.
     *
     * @param categoryId the ID of the category to delete.
     */
    @CacheEvict(value = "categories", key = "#categoryId")
    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
