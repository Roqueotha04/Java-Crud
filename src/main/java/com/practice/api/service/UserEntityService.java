package com.practice.api.service;

import com.practice.api.dto.UserEntityRequest;
import com.practice.api.dto.UserEntityResponse;

import java.util.List;

public interface UserEntityService {
    public UserEntityResponse save (UserEntityRequest userEntity);
    public List<UserEntityResponse> findAll();

}
