package dev.sagar.wordsmith.post;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PagedResponse<PostResponseDto>> getAllPosts(@RequestParam(defaultValue = "1") Integer page,
                                                                      @RequestParam(defaultValue = "10") Integer size,
                                                                      @RequestParam(defaultValue = "id") String sortBy,
                                                                      @RequestParam(defaultValue = "desc") String direction) {
        PagedResponse<PostResponseDto> posts = postService.getAllPosts(page, size, sortBy, direction);
        return ResponseEntity.status(200).body(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
        PostResponseDto post = postService.getPostById(postId);
        return ResponseEntity.status(200).body(post);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto post = postService.createPost(postRequestDto);
        return ResponseEntity.status(201).body(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto post = postService.updatePost(postId, postRequestDto);
        return ResponseEntity.status(200).body(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<PagedResponse<PostResponseDto>> getPostsByCategoryId(@PathVariable Integer categoryId,
                                                                               @RequestParam(defaultValue = "1") Integer page,
                                                                               @RequestParam(defaultValue = "10") Integer size,
                                                                               @RequestParam(defaultValue = "id") String sortBy,
                                                                               @RequestParam(defaultValue = "desc") String direction) {
        PagedResponse<PostResponseDto> posts = postService.getPostsByCategoryId(categoryId, page, size, sortBy, direction);
        return ResponseEntity.status(200).body(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PagedResponse<PostResponseDto>> getPostsByUserId(@PathVariable Long userId,
                                                                           @RequestParam(defaultValue = "1") Integer page,
                                                                           @RequestParam(defaultValue = "10") Integer size,
                                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                                           @RequestParam(defaultValue = "desc") String direction) {
        PagedResponse<PostResponseDto> posts = postService.getPostsByUserId(userId, page, size, sortBy, direction);
        return ResponseEntity.status(200).body(posts);
    }

    @GetMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PagedResponse<PostResponseDto>> getPostsByUserIdAndCategoryId(@PathVariable Long userId,
                                                                                        @PathVariable Integer categoryId,
                                                                                        @RequestParam(defaultValue = "1") Integer page,
                                                                                        @RequestParam(defaultValue = "10") Integer size,
                                                                                        @RequestParam(defaultValue = "id") String sortBy,
                                                                                        @RequestParam(defaultValue = "desc") String direction) {
        PagedResponse<PostResponseDto> posts = postService.getPostsByUserIdAndCategoryId(userId, categoryId, page, size, sortBy, direction);
        return ResponseEntity.status(200).body(posts);
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<PagedResponse<PostResponseDto>> getPostsBySearchTerm(@PathVariable String searchTerm,
                                                                               @RequestParam(defaultValue = "1") Integer page,
                                                                               @RequestParam(defaultValue = "10") Integer size,
                                                                               @RequestParam(defaultValue = "id") String sortBy,
                                                                               @RequestParam(defaultValue = "desc") String direction) {
        PagedResponse<PostResponseDto> posts = postService.getByTitleContaining(searchTerm, page, size, sortBy, direction);
        return ResponseEntity.status(200).body(posts);
    }
}
