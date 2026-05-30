package com.practice.api.repository;
import com.practice.api.entity.Post;
import com.practice.api.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUserEntityId(Long userId, Pageable pageable);

    List<Post> findByUserEntityUsername(String username, Sort sort);
}
