package com.practice.api.service;

import com.practice.api.entity.UserEntity;

import java.util.List;

public interface UserEntityService {
    public UserEntity save (UserEntity userEntity);
    public List<UserEntity> findAll();
}
