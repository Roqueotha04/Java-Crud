package com.practice.api.controller;

import com.practice.api.dto.PostRequest;
import com.practice.api.dto.PostResponse;
import com.practice.api.dto.UpdateContentDTO;
import com.practice.api.entity.Post;
import com.practice.api.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public ResponseEntity<Page<PostResponse>> findAll(@RequestParam (required = false) Long userId, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll(userId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PostResponse> savePost(@RequestBody @Valid PostRequest postRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.savePost(postRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody @Valid PostRequest postRequest){
        return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(id, postRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> patchContent(@PathVariable Long id, @RequestBody @Valid UpdateContentDTO updateContentDTO){
        return ResponseEntity.status(HttpStatus.OK).body(postService.patchContent(id, updateContentDTO.content()));
    }

    @DeleteMapping("/{id}") ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
