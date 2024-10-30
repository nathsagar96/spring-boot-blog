package com.codesmith.wordsmith.comment;

import com.codesmith.wordsmith.exception.ResourceNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing comment-related operations.
 *
 * <p>This class provides methods to handle business logic for creating, retrieving,
 * updating, and deleting comments. It interacts with the data persistence layer
 * through {@link CommentRepository} and uses {@link CommentMapper} to map
 * between entities and DTOs.</p>
 *
 * <p>Annotated with {@link Service} to denote it as a service component in the Spring context,
 * allowing for dependency injection.</p>
 *
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    /**
     * Retrieves a list of all comments.
     *
     * @return a list of {@link CommentResponseDto} representing all comments.
     */
    @Cacheable(value = "comments")
    public List<CommentResponseDto> getAllComments() {
        return commentRepository
                .findAll()
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a specific comment by its ID.
     *
     * @param commentId the ID of the comment to retrieve.
     * @return a {@link CommentResponseDto} representing the comment with the given ID.
     * @throws ResourceNotFoundException if no comment is found with the provided ID.
     */
    @Cacheable(value = "comments", key = "#commentId")
    public CommentResponseDto getCommentById(Long commentId) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        return commentMapper.toDto(comment);
    }

    /**
     * Creates a new comment based on the provided data.
     *
     * @param commentRequestDto the DTO containing the new comment data.
     * @return a {@link CommentResponseDto} representing the created comment.
     */
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Comment comment = commentMapper.toEntity(commentRequestDto);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    /**
     * Updates an existing comment with the provided data.
     *
     * @param commentId         the ID of the comment to update.
     * @param commentRequestDto the DTO containing the updated comment data.
     * @return a {@link CommentResponseDto} representing the updated comment.
     * @throws ResourceNotFoundException if no comment is found with the provided ID.
     */
    @CachePut(value = "comments", key = "#commentId")
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        comment.setContent(commentRequestDto.content());
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDto(updatedComment);
    }

    /**
     * Deletes a specific comment by its ID.
     *
     * @param commentId the ID of the comment to delete.
     */
    @CacheEvict(value = "comments", key = "#commentId")
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    /**
     * Retrieves comments by the ID of the associated post.
     *
     * @param postId the ID of the post to retrieve comments for.
     * @return a list of {@link CommentResponseDto} representing the comments associated with the given post.
     */
    @Cacheable(value = "comments", key = "#postId")
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        return commentRepository
                .findByPostId(postId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    /**
     * Retrieves comments by the ID of the user who made the comments.
     *
     * @param userId the ID of the user to retrieve comments for.
     * @return a list of {@link CommentResponseDto} representing the comments made by the specified user.
     */
    @Cacheable(value = "comments", key = "#userId")
    public List<CommentResponseDto> getCommentsByUserId(Long userId) {
        return commentRepository
                .findByUserId(userId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }
}
