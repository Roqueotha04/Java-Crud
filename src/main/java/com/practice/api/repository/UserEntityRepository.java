package com.practice.api.repository;

import com.practice.api.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
        Page<UserEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
