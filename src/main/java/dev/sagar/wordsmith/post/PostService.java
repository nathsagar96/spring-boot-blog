package dev.sagar.wordsmith.post;

import dev.sagar.wordsmith.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;

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

    public PagedResponse<PostResponseDto> getAllPosts(Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findAll(pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }

    public PostResponseDto getPostById(Long postId) {
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return postMapper.toDto(postEntity);
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        PostEntity postEntity = postMapper.toEntity(postRequestDto);
        PostEntity savedPostEntity = postRepository.save(postEntity);
        return postMapper.toDto(savedPostEntity);
    }

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

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public PagedResponse<PostResponseDto> getPostsByCategoryId(Integer categoryId, Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findByCategoryId(categoryId, pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }

    public PagedResponse<PostResponseDto> getPostsByUserId(Long userId, Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findByUserId(userId, pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }

    public PagedResponse<PostResponseDto> getPostsByUserIdAndCategoryId(Long userId, Integer categoryId, Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findByUserIdAndCategoryId(userId, categoryId, pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }

    public PagedResponse<PostResponseDto> getByTitleContaining(String title, Integer page, Integer size, String sortBy, String direction) {
        PageRequest pageRequest = createPageRequest(page, size, sortBy, direction);
        Page<PostResponseDto> postPage = postRepository.findByTitleContaining(title, pageRequest).map(postMapper::toDto);
        return createPagedResponse(postPage);
    }
}
