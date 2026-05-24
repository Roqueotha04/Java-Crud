package com.practice.api.service;

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
}
