package com.codesmith.wordsmith.comment;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
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

/**
 * REST controller for managing comments.
 *
 * <p>This class provides endpoints for creating, retrieving, updating,
 * and deleting comments, as well as retrieving comments by post and user IDs.
 * All endpoints require Bearer Authentication.</p>
 *
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Comment API", description = "APIs for managing comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * Retrieves a list of all comments.
     *
     * @return a list of {@link CommentResponseDto} representing all comments.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getAllComments() {
        return commentService.getAllComments();
    }

    /**
     * Retrieves a specific comment by its ID.
     *
     * @param commentId the ID of the comment to retrieve.
     * @return a {@link CommentResponseDto} representing the comment with the given ID.
     */
    @GetMapping("{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto getCommentById(@PathVariable final Long commentId) {
        return commentService.getCommentById(commentId);
    }

    /**
     * Creates a new comment with the provided data.
     *
     * @param commentRequestDto the DTO containing the new comment data.
     * @return a {@link CommentResponseDto} representing the created comment.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@Valid @RequestBody final CommentRequestDto commentRequestDto) {
        return commentService.createComment(commentRequestDto);
    }

    /**
     * Updates an existing comment with the provided data.
     *
     * @param commentId         the ID of the comment to update.
     * @param commentRequestDto the DTO containing the updated comment data.
     * @return a {@link CommentResponseDto} representing the updated comment.
     */
    @PutMapping("{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updateComment(
            @PathVariable final Long commentId,
            @Valid @RequestBody final CommentRequestDto commentRequestDto) {
        return commentService.updateComment(commentId, commentRequestDto);
    }

    /**
     * Deletes a specific comment by its ID.
     *
     * @param commentId the ID of the comment to delete.
     */
    @DeleteMapping("{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable final Long commentId) {
        commentService.deleteComment(commentId);
    }

    /**
     * Retrieves comments by the ID of the associated post.
     *
     * @param postId the ID of the post to retrieve comments for.
     * @return a list of {@link CommentResponseDto} representing the comments associated with the given post.
     */
    @GetMapping("/post/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getCommentsByPostId(@PathVariable final Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    /**
     * Retrieves comments by the ID of the user who made the comments.
     *
     * @param userId the ID of the user to retrieve comments for.
     * @return a list of {@link CommentResponseDto} representing the comments made by the specified user.
     */
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentResponseDto> getCommentsByUserId(@PathVariable final Long userId) {
        return commentService.getCommentsByUserId(userId);
    }
}
