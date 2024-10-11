package dev.sagar.wordsmith.comment;

import dev.sagar.wordsmith.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<CommentResponseDto> getAllComments() {
        return commentRepository
                .findAll()
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    public CommentResponseDto getCommentById(Long commentId) {
        CommentEntity commentEntity = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        return commentMapper.toDto(commentEntity);
    }

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        CommentEntity commentEntity = commentMapper.toEntity(commentRequestDto);
        CommentEntity savedCommentEntity = commentRepository.save(commentEntity);
        return commentMapper.toDto(savedCommentEntity);
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        CommentEntity commentEntity = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));

        commentEntity.setContent(commentRequestDto.content());
        CommentEntity updatedCommentEntity = commentRepository.save(commentEntity);
        return commentMapper.toDto(updatedCommentEntity);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        return commentRepository
                .findByPostId(postId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    public List<CommentResponseDto> getCommentsByUserId(Long userId) {
        return commentRepository
                .findByUserId(userId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

}
