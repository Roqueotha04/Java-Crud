package com.practice.api.provider;

import com.practice.api.entity.Post;
import com.practice.api.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

public class DataProvider {

    public List<UserEntity> getUserEntityList (){
        return List.of(
                new UserEntity(1L, "user1", "lastname1"),
                new UserEntity(2L, "user2", "lastname3"),
                new UserEntity(3L, "user2", "lastname3")
        );
    }

    public List<Post> getPostList() {
        UserEntity user1 = new UserEntity(1L, "user1", "lastname1");
        UserEntity user2 = new UserEntity(2L, "user2", "lastname2");

        return List.of(
                createPost(1L, "Post 1", "Content for post 1", LocalDateTime.now().minusDays(5), true, user1),
                createPost(2L, "Post 2", "Content for post 2", LocalDateTime.now().minusDays(10), false, user1),
                createPost(3L, "Post 3", "Content for post 3", LocalDateTime.now().minusDays(2), true, user2),
                createPost(4L, "Post 4", "Content for post 4", LocalDateTime.now().minusDays(15), true, user2),
                createPost(5L, "Post 5", "Content for post 5", LocalDateTime.now().minusDays(1), false, user1)
        );
    }

    private Post createPost(Long id, String title, String content, LocalDateTime createdAt, boolean published, UserEntity user) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(createdAt);
        post.setPublished(published);
        post.setUserEntity(user);
        return post;
    }
}
