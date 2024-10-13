package dev.sagar.wordsmith.category;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing categories.
 *
 * <p>This class provides endpoints for creating, retrieving, updating,
 * and deleting category resources. All endpoints require
 * Bearer Authentication.</p>
 *
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Category API", description = "APIs for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Retrieves a list of all categories.
     *
     * @return a list of {@link CategoryResponseDto} representing all categories.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Retrieves a specific category by its ID.
     *
     * @param categoryId the ID of the category to retrieve.
     * @return a {@link CategoryResponseDto} representing the category with the given ID.
     */
    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto getCategoryById(@PathVariable final Integer categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    /**
     * Creates a new category with the provided data.
     *
     * @param categoryRequestDto the data for the new category.
     * @return a {@link CategoryResponseDto} representing the created category.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@Valid @RequestBody final CategoryRequestDto categoryRequestDto) {
        return categoryService.createCategory(categoryRequestDto);
    }

    /**
     * Updates an existing category with the provided data.
     *
     * @param categoryId         the ID of the category to update.
     * @param categoryRequestDto the updated data for the category.
     * @return a {@link CategoryResponseDto} representing the updated category.
     */
    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto updateCategory(
            @PathVariable final Integer categoryId,
            @Valid @RequestBody final CategoryRequestDto categoryRequestDto) {
        return categoryService.updateCategory(categoryId, categoryRequestDto);
    }

    /**
     * Deletes a specific category by its ID.
     *
     * @param categoryId the ID of the category to delete.
     */
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable final Integer categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
