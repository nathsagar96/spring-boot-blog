package dev.sagar.wordsmith.post;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static dev.sagar.wordsmith.post.PageConstants.PAGE_NUMBER;
import static dev.sagar.wordsmith.post.PageConstants.PAGE_SIZE;
import static dev.sagar.wordsmith.post.PageConstants.SORT_BY;
import static dev.sagar.wordsmith.post.PageConstants.SORT_DIRECTION;

/**
 * Controller class for managing post-related operations.
 *
 * <p>This class handles incoming HTTP requests related to posts,
 * including creating, retrieving, updating, and deleting posts.
 * It utilizes the {@link PostService} to perform business logic and return
 * appropriate responses.</p>
 *
 * <p>Annotated with {@link RestController} to designate it as a Spring
 * MVC controller. It also uses {@link RequiredArgsConstructor} for
 * constructor-based dependency injection.</p>
 *
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Post API", description = "APIs for managing posts")
public class PostController {

    private final PostService postService;

    /**
     * Retrieves a paginated list of all posts.
     *
     * @param page      the page number to retrieve (default is defined by {@code PAGE_NUMBER}).
     * @param size      the number of posts per page (default is defined by {@code PAGE_SIZE}).
     * @param sortBy    the field to sort by (default is defined by {@code SORT_BY}).
     * @param direction the direction to sort (default is defined by {@code SORT_DIRECTION}).
     * @return a {@link PagedResponse} containing the list of {@link PostResponseDto}.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getAllPosts(
            @RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
            @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
            @RequestParam(defaultValue = SORT_BY) final String sortBy,
            @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getAllPosts(page, size, sortBy, direction);
    }

    /**
     * Retrieves a specific post by its ID.
     *
     * @param postId the ID of the post to retrieve.
     * @return a {@link PostResponseDto} representing the requested post.
     */
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto getPostById(@PathVariable final Long postId) {
        return postService.getPostById(postId);
    }

    /**
     * Creates a new post based on the provided data.
     *
     * @param postRequestDto the DTO containing the new post data.
     * @return a {@link PostResponseDto} representing the created post.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost(@Valid @RequestBody final PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }

    /**
     * Updates an existing post with the provided data.
     *
     * @param postId         the ID of the post to update.
     * @param postRequestDto the DTO containing the updated post data.
     * @return a {@link PostResponseDto} representing the updated post.
     */
    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto updatePost(@PathVariable final Long postId,
                                      @Valid @RequestBody final PostRequestDto postRequestDto) {
        return postService.updatePost(postId, postRequestDto);
    }

    /**
     * Deletes a specific post by its ID.
     *
     * @param postId the ID of the post to delete.
     */
    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable final Long postId) {
        postService.deletePost(postId);
    }

    /**
     * Retrieves a paginated list of posts by category ID.
     *
     * @param categoryId the ID of the category to filter posts by.
     * @param page       the page number to retrieve (default is defined by {@code PAGE_NUMBER}).
     * @param size       the number of posts per page (default is defined by {@code PAGE_SIZE}).
     * @param sortBy     the field to sort by (default is defined by {@code SORT_BY}).
     * @param direction  the direction to sort (default is defined by {@code SORT_DIRECTION}).
     * @return a {@link PagedResponse} containing the list of {@link PostResponseDto}.
     */
    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getPostsByCategoryId(
            @PathVariable final Integer categoryId,
            @RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
            @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
            @RequestParam(defaultValue = SORT_BY) final String sortBy,
            @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getPostsByCategoryId(categoryId, page, size, sortBy, direction);
    }

    /**
     * Retrieves a paginated list of posts by user ID.
     *
     * @param userId    the ID of the user to filter posts by.
     * @param page      the page number to retrieve (default is defined by {@code PAGE_NUMBER}).
     * @param size      the number of posts per page (default is defined by {@code PAGE_SIZE}).
     * @param sortBy    the field to sort by (default is defined by {@code SORT_BY}).
     * @param direction the direction to sort (default is defined by {@code SORT_DIRECTION}).
     * @return a {@link PagedResponse} containing the list of {@link PostResponseDto}.
     */
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getPostsByUserId(
            @PathVariable final Long userId,
            @RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
            @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
            @RequestParam(defaultValue = SORT_BY) final String sortBy,
            @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getPostsByUserId(userId, page, size, sortBy, direction);
    }

    /**
     * Retrieves a paginated list of posts by user ID and category ID.
     *
     * @param userId     the ID of the user to filter posts by.
     * @param categoryId the ID of the category to filter posts by.
     * @param page       the page number to retrieve (default is defined by {@code PAGE_NUMBER}).
     * @param size       the number of posts per page (default is defined by {@code PAGE_SIZE}).
     * @param sortBy     the field to sort by (default is defined by {@code SORT_BY}).
     * @param direction  the direction to sort (default is defined by {@code SORT_DIRECTION}).
     * @return a {@link PagedResponse} containing the list of {@link PostResponseDto}.
     */
    @GetMapping("/user/{userId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getPostsByUserIdAndCategoryId(
            @PathVariable final Long userId,
            @PathVariable final Integer categoryId,
            @RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
            @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
            @RequestParam(defaultValue = SORT_BY) final String sortBy,
            @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getPostsByUserIdAndCategoryId(userId, categoryId, page, size, sortBy, direction);
    }

    /**
     * Retrieves a paginated list of posts based on a search term.
     *
     * @param searchTerm the term to search for in post titles.
     * @param page       the page number to retrieve (default is defined by {@code PAGE_NUMBER}).
     * @param size       the number of posts per page (default is defined by {@code PAGE_SIZE}).
     * @param sortBy     the field to sort by (default is defined by {@code SORT_BY}).
     * @param direction  the direction to sort (default is defined by {@code SORT_DIRECTION}).
     * @return a {@link PagedResponse} containing the list of {@link PostResponseDto} that match the search term.
     */
    @GetMapping("/search/{searchTerm}")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getPostsBySearchTerm(
            @PathVariable final String searchTerm,
            @RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
            @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
            @RequestParam(defaultValue = SORT_BY) final String sortBy,
            @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getByTitleContaining(searchTerm, page, size, sortBy, direction);
    }
}
