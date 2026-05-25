package com.practice.api.service;

import com.practice.api.dto.PostRequest;
import com.practice.api.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    public Page<PostResponse> findAll(Long id, Pageable pageable);
    public PostResponse findById(Long id);
    public PostResponse savePost(PostRequest postRequest);
    public PostResponse updatePost(Long id, PostRequest postRequest);
    public PostResponse patchContent(Long id, String content);
    public void delete(Long id);
}
