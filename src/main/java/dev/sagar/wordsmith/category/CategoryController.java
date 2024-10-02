package dev.sagar.wordsmith.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.status(200).body(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Integer categoryId) {
        CategoryResponseDto category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(200).body(category);
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto category = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.status(201).body(category);
    }

    @PutMapping
    public ResponseEntity<CategoryResponseDto> updateCategory(@PathVariable Integer categoryId, @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        CategoryResponseDto category = categoryService.updateCategory(categoryId, categoryRequestDto);
        return ResponseEntity.status(200).body(category);
    }


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(204).build();
    }
}
