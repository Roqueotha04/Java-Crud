package com.practice.api.service;

import com.practice.api.dto.PostRequest;
import com.practice.api.dto.PostResponse;
import com.practice.api.entity.Post;
import com.practice.api.entity.UserEntity;
import com.practice.api.exception.ResourceNotFoundException;
import com.practice.api.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserEntityService userEntityService;

    public PostServiceImpl(PostRepository postRepository, UserEntityService userEntityService) {
        this.postRepository = postRepository;
        this.userEntityService = userEntityService;
    }

    @Override
    public Page<PostResponse> findAll(Long id, Pageable pageable) {
        if(id==null){
            return postRepository.findAll(pageable).map(this::toResponse);
        }else{
            return postRepository.findByUserEntityId(id, pageable).map(this::toResponse);
        }
    }

    @Override
    public PostResponse findById(Long id) {
        return toResponse(getPostOrThrow(id));
    }

    @Override
    public PostResponse savePost(PostRequest postRequest) {
        Post post = toPost(postRequest);
        return toResponse(postRepository.save(post));
    }

    @Override
    public PostResponse updatePost(Long id, PostRequest postRequest) {
        Post post = getPostOrThrow(id);
        post.setTitle(postRequest.title());
        post.setContent(postRequest.content());
        post.setPublished(postRequest.published());
        return toResponse(postRepository.save(post));
    }

    @Override
    public PostResponse patchContent(Long id, String content) {
        Post post = getPostOrThrow(id);
        post.setContent(content);
        return toResponse(postRepository.save(post));
    }

    @Override
    public void delete(Long id) {
        Post post = getPostOrThrow(id);
        postRepository.delete(post);
    }

    private Post getPostOrThrow(Long id){
        return postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post not found with id: " +id));
    }

    private PostResponse toResponse(Post post){
        return new PostResponse(post.getTitle(), post.getContent(), post.getCreatedAt(), post.isPublished(), post.getUserEntity().getId(), post.getUserEntity().getName());
    }

    private Post toPost(PostRequest postRequest){
        Post post = new Post();
        post.setTitle(postRequest.title());
        post.setContent(postRequest.content());
        post.setPublished(postRequest.published());
        post.setCreatedAt(postRequest.createdAt() != null ? postRequest.createdAt() : LocalDateTime.now());

        UserEntity userEntity = userEntityService.findEntityById(postRequest.userId());
        post.setUserEntity(userEntity);
        return post;
    }
}
