package com.practice.api.service;

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


    public UserEntity save (UserEntity userEntity){
        return userEntityRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return userEntityRepository.findAll();
    }
}
