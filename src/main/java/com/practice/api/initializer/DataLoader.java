package com.practice.api.initializer;

import com.practice.api.entity.Post;
import com.practice.api.entity.RoleEntity;
import com.practice.api.entity.RoleEnum;
import com.practice.api.entity.UserEntity;
import com.practice.api.repository.PostRepository;
import com.practice.api.repository.RoleEntityRepository;
import com.practice.api.repository.UserEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserEntityRepository userRepository;
    private final PostRepository postRepository;
    private final RoleEntityRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserEntityRepository userRepository, PostRepository postRepository,
                      RoleEntityRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        RoleEntity roleAdmin = roleRepository.save(new RoleEntity(null, RoleEnum.ADMIN));
        RoleEntity roleUser  = roleRepository.save(new RoleEntity(null, RoleEnum.USER));
        RoleEntity roleGuest = roleRepository.save(new RoleEntity(null, RoleEnum.GUEST));

        UserEntity user1 = new UserEntity(null, "Roque", "roque_f", passwordEncoder.encode("admin123"), "Fernandez");
        user1.setRoleEntitySet(Set.of(roleAdmin, roleUser));
        userRepository.save(user1);

        UserEntity user2 = new UserEntity(null, "Juan", "juan_p", passwordEncoder.encode("user123"), "Perez");
        user2.setRoleEntitySet(Set.of(roleUser));
        userRepository.save(user2);

        UserEntity user3 = new UserEntity(null, "Maria", "maria_l", passwordEncoder.encode("user123"), "Lopez");
        user3.setRoleEntitySet(Set.of(roleUser, roleGuest));
        userRepository.save(user3);

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
