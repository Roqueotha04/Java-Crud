package com.practice.api.service;

import com.practice.api.dto.UserEntityRequest;
import com.practice.api.dto.UserEntityResponse;
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


    public UserEntityResponse save (UserEntityRequest userEntity){
        return toResponse(userEntityRepository.save(toUser(userEntity)));
    }

    @Override
    public List<UserEntityResponse> findAll() {
        return userEntityRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private UserEntity toUser (UserEntityRequest userEntityRequest){
        UserEntity user = new UserEntity();
        user.setName(userEntityRequest.name());
        user.setLastName(userEntityRequest.lastName());
        return user;
    }

    private UserEntityResponse toResponse (UserEntity userEntity){
        return new UserEntityResponse(userEntity.getName(), userEntity.getLastName());
    }
}
