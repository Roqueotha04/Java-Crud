package com.practice.api.service;

import com.practice.api.dto.UserEntityRequest;
import com.practice.api.dto.UserEntityResponse;
import com.practice.api.entity.UserEntity;
import com.practice.api.provider.DataProvider;
import com.practice.api.repository.UserEntityRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceImplTest {

    private final DataProvider dataProvider = new DataProvider();

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
        Pageable pageable = PageRequest.of(0,3);
        Page<UserEntity> page = new PageImpl<>(userEntityList, pageable, 6);
        when (userEntityRepository.findAll(any(Pageable.class)))
                .thenReturn(page);

        Page<UserEntityResponse> result = userEntityServiceImpl.findAll(null, pageable);
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(page.getTotalPages(), result.getTotalPages());
        assertEquals(userEntityList.get(0).getName(), result.getContent().get(0).name());

        verify(userEntityRepository).findAll(any(Pageable.class));
    }

    @Test
    void testFindAllByUserId(){
        String name = "pepe";
        List<UserEntity> userEntityList = dataProvider.getUserEntityList();
        Pageable pageable = PageRequest.of(0,3);
        Page<UserEntity> page = new PageImpl<>(userEntityList, pageable, 6);
        when (userEntityRepository.findByNameContainingIgnoreCase(eq(name), any(Pageable.class)))
                .thenReturn(page);

        Page<UserEntityResponse> result = userEntityServiceImpl.findAll(name, pageable);
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(page.getTotalPages(), result.getTotalPages());
        assertEquals(userEntityList.get(0).getName(), result.getContent().get(0).name());

        verify(userEntityRepository).findByNameContainingIgnoreCase(eq(name), any(Pageable.class));
    }

    @Test
    void testFindById(){
        UserEntity userEntity = dataProvider.getUserEntityList().get(0);

        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        UserEntityResponse user = userEntityServiceImpl.findById(1L);

        assertNotNull(user);
        assertEquals(userEntity.getName(), user.name());
        assertEquals(userEntity.getLastName(), user.lastName());

        verify(userEntityRepository).findById(1L);
    }

    @Test
    void testUpdate(){
        UserEntity userEntity = dataProvider.getUserEntityList().get(0);
        UserEntityRequest userEntityRequest = new UserEntityRequest(userEntity.getName(), "pepe");
        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userEntityRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserEntityResponse result = userEntityServiceImpl.updateUser(1L, userEntityRequest);

        assertEquals(userEntity.getName(), result.name());
        assertEquals("pepe", result.lastName());

        verify(userEntityRepository).findById(1L);
        verify(userEntityRepository).save(any(UserEntity.class));
    }

    @Test
    void testPatchLastName(){
        UserEntity userEntity = dataProvider.getUserEntityList().get(0);
        String lastname = "pepe";
        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userEntityRepository.save(any(UserEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserEntityResponse result = userEntityServiceImpl.updateLastName(1L, lastname);

        assertEquals(userEntity.getName(), result.name());
        assertEquals("pepe", result.lastName());

        verify(userEntityRepository).findById(1L);
        verify(userEntityRepository).save(any(UserEntity.class));
    }

    @Test
    void testDelete(){
        UserEntity userEntity = dataProvider.getUserEntityList().get(0);
        when(userEntityRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        userEntityServiceImpl.delete(1L);
        verify(userEntityRepository).findById(1L);
        verify(userEntityRepository).delete(userEntity);
    }
}
