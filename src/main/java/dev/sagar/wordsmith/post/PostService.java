package dev.sagar.wordsmith.post;

import dev.sagar.wordsmith.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Service class for managing posts in the application.
 *
 * <p>This class provides methods to create, read, update, and delete posts, as well as retrieve
 * paginated lists of posts based on various criteria, including category and user ID.</p>
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;

    /**
     * Creates a PageRequest object for pagination.
     *
     * @param page      the page number (1-based).
     * @param size      the size of each page.
     * @param sortBy    the field to sort by.
     * @param direction the sorting direction ('ASC' or 'DESC').
     * @return a PageRequest object.
     * @throws IllegalArgumentException if the sorting direction is invalid or if the sortBy field is null or empty.
     */
    private PageRequest createPageRequest(Integer page, Integer size, String sortBy, String direction) {
        // Ensure the page number is at least 0 (zero-based indexing)
        int validatedPage = (page != null && page > 0) ? page - 1 : 0;

        if (direction == null || (!direction.equalsIgnoreCase("ASC") && !direction.equalsIgnoreCase("DESC"))) {
            throw new IllegalArgumentException("Invalid sorting direction. Use 'ASC' or 'DESC'.");
        }

        if (sortBy == null || sortBy.trim().isEmpty()) {
            throw new IllegalArgumentException("SortBy field cannot be null or empty.");
        }

        Sort sort = Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), sortBy);
        return PageRequest.of(validatedPage, size, sort);
    }

    /**
     * Creates a PagedResponse object from a Page of PostResponseDto.
     *
     * @param postPage the Page of PostResponseDto to convert.
     * @return a PagedResponse containing the content and pagination information.
     */
    private PagedResponse<PostResponseDto> createPagedResponse(Page<PostResponseDto> postPage) {
        return new PagedResponse<>(
                postPage.getContent(),
                postPage.getNumber() + 1,  // Converting to 1-based page numbering
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages(),
                postPage.isLast()
        );
    }

    /**
     * Retrieves all posts with pagination and sorting.
     *
     * @param page      the page number (1-based).
     * @param size      the size of each page.
     * @param sortBy    the field to sort by.
     * @param direction the sorting direction ('ASC' or 'DESC').
     * @return a PagedResponse containing the list of PostResponseDto.
     */
    public PagedResponse<PostResponseDto> getAllPosts(Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findAll(pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param postId the ID of the post to retrieve.
     * @return the PostResponseDto representing the found post.
     * @throws ResourceNotFoundException if the post is not found.
     */
    @Cacheable(value = "post", key = "#postId")
    public PostResponseDto getPostById(Long postId) {
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return postMapper.toDto(postEntity);
    }

    /**
     * Creates a new post.
     *
     * @param postRequestDto the request DTO containing the details of the post to create.
     * @return the PostResponseDto representing the created post.
     */
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        PostEntity postEntity = postMapper.toEntity(postRequestDto);
        PostEntity savedPostEntity = postRepository.save(postEntity);
        return postMapper.toDto(savedPostEntity);
    }

    /**
     * Updates an existing post.
     *
     * @param postId         the ID of the post to update.
     * @param postRequestDto the request DTO containing the updated details of the post.
     * @return the PostResponseDto representing the updated post.
     * @throws ResourceNotFoundException if the post is not found.
     */
    @CachePut(value = "post", key = "#postId")
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto) {
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        postEntity.setTitle(postRequestDto.title());
        postEntity.setContent(postRequestDto.content());
        postEntity.setCategoryId(postRequestDto.categoryId());

        PostEntity updatedPostEntity = postRepository.save(postEntity);
        return postMapper.toDto(updatedPostEntity);
    }

    /**
     * Deletes a post by its ID.
     *
     * @param postId the ID of the post to delete.
     */
    @CacheEvict(value = "post", key = "#postId")
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    /**
     * Retrieves posts by category ID with pagination and sorting.
     *
     * @param categoryId the ID of the category to filter posts.
     * @param page       the page number (1-based).
     * @param size       the size of each page.
     * @param sortBy     the field to sort by.
     * @param direction  the sorting direction ('ASC' or 'DESC').
     * @return a PagedResponse containing the list of PostResponseDto for the specified category.
     */
    public PagedResponse<PostResponseDto> getPostsByCategoryId(Integer categoryId, Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findByCategoryId(categoryId, pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }

    /**
     * Retrieves posts by user ID with pagination and sorting.
     *
     * @param userId    the ID of the user to filter posts.
     * @param page      the page number (1-based).
     * @param size      the size of each page.
     * @param sortBy    the field to sort by.
     * @param direction the sorting direction ('ASC' or 'DESC').
     * @return a PagedResponse containing the list of PostResponseDto for the specified user.
     */
    public PagedResponse<PostResponseDto> getPostsByUserId(Long userId, Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findByUserId(userId, pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }

    /**
     * Retrieves posts by user ID and category ID with pagination and sorting.
     *
     * @param userId     the ID of the user to filter posts.
     * @param categoryId the ID of the category to filter posts.
     * @param page       the page number (1-based).
     * @param size       the size of each page.
     * @param sortBy     the field to sort by.
     * @param direction  the sorting direction ('ASC' or 'DESC').
     * @return a PagedResponse containing the list of PostResponseDto for the specified user and category.
     */
    public PagedResponse<PostResponseDto> getPostsByUserIdAndCategoryId(Long userId, Integer categoryId, Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findByUserIdAndCategoryId(userId, categoryId, pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }

    /**
     * Retrieves posts that contain a specified title search term with pagination and sorting.
     *
     * @param title     the title search term.
     * @param page      the page number (1-based).
     * @param size      the size of each page.
     * @param sortBy    the field to sort by.
     * @param direction the sorting direction ('ASC' or 'DESC').
     * @return a PagedResponse containing the list of PostResponseDto that match the search term.
     */
    public PagedResponse<PostResponseDto> getByTitleContaining(String title, Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findByTitleContaining(title, pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }
}
