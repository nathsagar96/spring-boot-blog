package dev.sagar.wordsmith.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

  List<CommentEntity> findByPostId(Long postId);

  List<CommentEntity> findByUserId(Long userId);
}
