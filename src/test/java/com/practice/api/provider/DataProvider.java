package com.practice.api.provider;

import com.practice.api.entity.UserEntity;

import java.util.List;

public class DataProvider {

    public List<UserEntity> getUserEntityList (){
        return List.of(
                new UserEntity(1L, "user1", "lastname1"),
                new UserEntity(2L, "user2", "lastname3"),
                new UserEntity(3L, "user2", "lastname3")
        );
    }
}
