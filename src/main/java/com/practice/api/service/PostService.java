package com.practice.api.service;

import com.practice.api.dto.PostRequest;
import com.practice.api.dto.PostResponse;

import java.util.List;

public interface PostService {
    public List<PostResponse> findAll();
    public PostResponse findById(Long id);
    public PostResponse savePost(PostRequest postRequest);
    public PostResponse updatePost(Long id, PostRequest postRequest);
    public PostResponse patchContent(Long id, String content);
    public void delete(Long id);
}
