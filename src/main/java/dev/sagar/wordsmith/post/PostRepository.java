package dev.sagar.wordsmith.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAll(Pageable pageable);

    Page<PostEntity> findByCategoryId(Integer categoryId, Pageable pageable);

    Page<PostEntity> findByUserId(Long userId, Pageable pageable);

    Page<PostEntity> findByUserIdAndCategoryId(Long userId, Integer categoryId, Pageable pageable);

    Page<PostEntity> findByTitleContaining(String title, Pageable pageable);
}
