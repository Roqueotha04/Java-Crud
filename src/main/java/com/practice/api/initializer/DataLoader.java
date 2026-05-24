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
        for (int i = 1; i <= 6; i++) {
            Post post = new Post();
            post.setTitle("Post " + i + " de " + user.getName());
            post.setContent("Contenido del post " + i);
            post.setCreatedAt(LocalDateTime.now().minusDays(6 - i));
            post.setPublished(i % 2 == 0);
            post.setUserEntity(user);
            postRepository.save(post);
        }
    }
}