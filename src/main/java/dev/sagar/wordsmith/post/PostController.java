package dev.sagar.wordsmith.post;

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

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getAllPosts(@RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
                                                      @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
                                                      @RequestParam(defaultValue = SORT_BY) final String sortBy,
                                                      @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getAllPosts(page, size, sortBy, direction);
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto getPostById(@PathVariable final Long postId) {
        return postService.getPostById(postId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto createPost(@Valid @RequestBody final PostRequestDto postRequestDto) {
        return postService.createPost(postRequestDto);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponseDto updatePost(@PathVariable final Long postId,
                                      @Valid @RequestBody final PostRequestDto postRequestDto) {
        return postService.updatePost(postId, postRequestDto);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable final Long postId) {
        postService.deletePost(postId);
    }

    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getPostsByCategoryId(@PathVariable final Integer categoryId,
                                                               @RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
                                                               @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
                                                               @RequestParam(defaultValue = SORT_BY) final String sortBy,
                                                               @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getPostsByCategoryId(categoryId, page, size, sortBy, direction);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getPostsByUserId(@PathVariable final Long userId,
                                                           @RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
                                                           @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
                                                           @RequestParam(defaultValue = SORT_BY) final String sortBy,
                                                           @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getPostsByUserId(userId, page, size, sortBy, direction);
    }

    @GetMapping("/user/{userId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getPostsByUserIdAndCategoryId(@PathVariable final Long userId,
                                                                        @PathVariable final Integer categoryId,
                                                                        @RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
                                                                        @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
                                                                        @RequestParam(defaultValue = SORT_BY) final String sortBy,
                                                                        @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getPostsByUserIdAndCategoryId(userId, categoryId, page, size, sortBy, direction);
    }

    @GetMapping("/search/{searchTerm}")
    @ResponseStatus(HttpStatus.OK)
    public PagedResponse<PostResponseDto> getPostsBySearchTerm(@PathVariable final String searchTerm,
                                                               @RequestParam(defaultValue = PAGE_NUMBER) final Integer page,
                                                               @RequestParam(defaultValue = PAGE_SIZE) final Integer size,
                                                               @RequestParam(defaultValue = SORT_BY) final String sortBy,
                                                               @RequestParam(defaultValue = SORT_DIRECTION) final String direction) {
        return postService.getByTitleContaining(searchTerm, page, size, sortBy, direction);
    }
}
