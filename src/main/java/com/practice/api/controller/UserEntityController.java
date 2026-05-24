package com.practice.api.controller;

import com.practice.api.dto.UpdateLastNameDTO;
import com.practice.api.dto.UserEntityRequest;
import com.practice.api.dto.UserEntityResponse;
import com.practice.api.service.UserEntityService;
import com.practice.api.service.UserEntityServiceImpl;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserEntityResponse> saveUser (@RequestBody @Valid UserEntityRequest userEntity){
        UserEntityResponse user = userEntityService.save(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping()
    public ResponseEntity<List<UserEntityResponse>> findAll(){
        List<UserEntityResponse> users = userEntityService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntityResponse> findById(@PathVariable Long id){
        UserEntityResponse userEntityResponse = userEntityService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userEntityResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntityResponse> updateUser (@PathVariable Long id, @RequestBody @Valid UserEntityRequest userEntityRequest){
        UserEntityResponse user = userEntityService.updateUser(id, userEntityRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserEntityResponse> patchLastName (@PathVariable Long id, @RequestBody @Valid UpdateLastNameDTO lastName){
        UserEntityResponse user = userEntityService.updateLastName(id, lastName.name());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id){
        userEntityService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
