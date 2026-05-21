package com.practice.api.controller;

import com.practice.api.dto.UserEntityRequest;
import com.practice.api.dto.UserEntityResponse;
import com.practice.api.service.UserEntityService;
import com.practice.api.service.UserEntityServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserEntityController {

    private final UserEntityService userEntityService;

    public UserEntityController(UserEntityServiceImpl userEntityServiceImpl) {
        this.userEntityService = userEntityServiceImpl;
    }

    @PostMapping("/save")
    public ResponseEntity<UserEntityResponse> saveUser (@RequestBody UserEntityRequest userEntity){
        UserEntityResponse user = userEntityService.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping()
    public ResponseEntity<List<UserEntityResponse>> findAll(){
        List<UserEntityResponse> users = userEntityService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
