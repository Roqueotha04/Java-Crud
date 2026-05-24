package com.practice.api.initializer;

import com.practice.api.entity.Post;
import com.practice.api.entity.UserEntity;
import com.practice.api.repository.PostRepository;
import com.practice.api.repository.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserEntityRepository userRepository;
    private final PostRepository postRepository;

    public DataLoader(UserEntityRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... args) {
        UserEntity user1 = userRepository.save(new UserEntity(null, "Roque", "Fernandez"));
        UserEntity user2 = userRepository.save(new UserEntity(null, "Juan", "Perez"));
        UserEntity user3 = userRepository.save(new UserEntity(null, "Maria", "Lopez"));

        createPostsForUser(user1);
        createPostsForUser(user2);
        createPostsForUser(user3);
    }

    private void createPostsForUser(UserEntity user) {
        postRepository.save(createPost(user, 1, "Introducción a Spring Boot", 45, true));
        postRepository.save(createPost(user, 2, "JPA y Hibernate explicado", 30, true));
        postRepository.save(createPost(user, 3, "Guía de testing unitario", 15, false));
        postRepository.save(createPost(user, 4, "REST API best practices", 8, true));
        postRepository.save(createPost(user, 5, "Docker para desarrolladores", 3, true));
        postRepository.save(createPost(user, 6, "Microservicios con Spring", 1, false));
    }

    private Post createPost(UserEntity user, int number, String title, int daysAgo, boolean published) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent("Contenido detallado del post: " + title);
        post.setCreatedAt(LocalDateTime.now().minusDays(daysAgo));
        post.setPublished(published);
        post.setUserEntity(user);
        return post;
    }
}