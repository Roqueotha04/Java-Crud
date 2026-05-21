package com.practice.api.service;

import com.practice.api.dto.UserEntityRequest;
import com.practice.api.entity.UserEntity;
import com.practice.api.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntityServiceImpl implements UserEntityService{

    private final UserEntityRepository userEntityRepository;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }


    public com.practice.api.dto.UserEntityResponse save (UserEntityRequest userEntity){
        return toResponse(userEntityRepository.save(toUser(userEntity)));
    }

    @Override
    public List<com.practice.api.dto.UserEntityResponse> findAll() {
        return userEntityRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public UserEntity toUser (UserEntityRequest userEntityRequest){
        UserEntity user = new UserEntity();
        user.setName(userEntityRequest.name());
        user.setName(userEntityRequest.lastName());
        return user;
    }

    public com.practice.api.dto.UserEntityResponse toResponse (UserEntity userEntity){
        return new com.practice.api.dto.UserEntityResponse(userEntity.getName(), userEntity.getLastName());
    }
}
