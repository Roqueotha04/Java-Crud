package com.practice.api.controller;

import com.practice.api.entity.UserEntity;
import com.practice.api.service.UserEntityService;
import com.practice.api.service.UserEntityServiceImpl;
import org.apache.catalina.User;
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
    public ResponseEntity<UserEntity> saveUser (@RequestBody UserEntity userEntity){
        UserEntity user = userEntityService.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping()
    public ResponseEntity<List<UserEntity>> findAll(){
        List<UserEntity> users = userEntityService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
