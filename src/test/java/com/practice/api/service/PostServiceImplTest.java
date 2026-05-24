package com.practice.api.service;

import com.practice.api.dto.PostRequest;
import com.practice.api.dto.PostResponse;
import com.practice.api.entity.Post;
import com.practice.api.provider.DataProvider;
import com.practice.api.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    private final DataProvider dataProvider = new DataProvider();

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserEntityService userEntityService;

    @InjectMocks
    private PostServiceImpl postService;


    @Test
    public void testFindAll(){
        List<Post> postList = dataProvider.getPostList();

        when(postRepository.findAll())
                .thenReturn(postList);

        List<PostResponse> result = postService.findAll();

        assertEquals(postList.size(), result.size());
        assertEquals(postList.get(1).getTitle(), result.get(1).title());

        verify(postRepository).findAll();
    }

    @Test
    public void testFindById(){
        Post post = dataProvider.getPostList().get(0);

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostResponse result = postService.findById(1L);
        assertEquals(result.title(), post.getTitle());
        assertEquals(result.content(), post.getContent());

        verify(postRepository).findById(1L);
    }

    @Test
    public void testSave(){
        Post post = dataProvider.getPostList().get(0);
        PostRequest postRequest = new PostRequest(post.getTitle(), post.getContent(), post.getCreatedAt(), post.isPublished(), post.getUserEntity().getId());

        when(userEntityService.findEntityById(post.getUserEntity().getId()))
                .thenReturn(post.getUserEntity());
        when(postRepository.save(any(Post.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PostResponse result = postService.savePost(postRequest);
        assertEquals(post.getTitle(), result.title());

        verify(postRepository).save(any(Post.class));
    }
}
