package com.practice.api.service;

import com.practice.api.dto.UserEntityRequest;
import com.practice.api.dto.UserEntityResponse;
import com.practice.api.entity.UserEntity;

import java.util.List;

public interface UserEntityService {
    public UserEntityResponse save(UserEntityRequest userEntity);

    public List<UserEntityResponse> findAll();

    public UserEntity findEntityById(Long id);

    public UserEntityResponse findById(Long id);

    public UserEntityResponse updateUser(Long id, UserEntityRequest userEntityRequest);

    public void delete(Long id);

    public UserEntityResponse updateLastName(Long id, String lastName);
}
