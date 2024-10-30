package com.codesmith.wordsmith.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAll(Pageable pageable);

  Page<Post> findByCategoryId(Integer categoryId, Pageable pageable);

  Page<Post> findByUserId(Long userId, Pageable pageable);

  Page<Post> findByUserIdAndCategoryId(Long userId, Integer categoryId, Pageable pageable);

  Page<Post> findByTitleContaining(String title, Pageable pageable);
}
