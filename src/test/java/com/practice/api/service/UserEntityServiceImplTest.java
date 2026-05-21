package com.practice.api.service;

import com.practice.api.dto.UserEntityRequest;
import com.practice.api.dto.UserEntityResponse;
import com.practice.api.entity.UserEntity;
import com.practice.api.provider.DataProvider;
import com.practice.api.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceImplTest {

    private DataProvider dataProvider;

    @Mock
    private UserEntityRepository userEntityRepository;

    @InjectMocks
    private UserEntityServiceImpl userEntityServiceImpl;

    @Test
    void testSave(){
        UserEntityRequest userEntityRequest = new UserEntityRequest("pepe", "Cacho");

        when(userEntityRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserEntityResponse result = userEntityServiceImpl.save(userEntityRequest);
        assertEquals(userEntityRequest.name(), result.name());
        assertEquals(userEntityRequest.lastName(), result.lastName());
        verify(userEntityRepository).save(any(UserEntity.class));
    }

    @Test
    void testFindAll(){
        List<UserEntity> userEntityList = dataProvider.getUserEntityList();

        when (userEntityRepository.findAll())
                .thenReturn(userEntityList);

        List<UserEntityResponse> result = userEntityServiceImpl.findAll();
        assertEquals(userEntityList.size(), result.size());
        assertEquals(userEntityList.get(0).getName(), result.get(0).name());
    }


}
