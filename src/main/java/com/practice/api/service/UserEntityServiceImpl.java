package com.practice.api.service;

import com.practice.api.dto.UserEntityRequest;
import com.practice.api.dto.UserEntityResponse;
import com.practice.api.entity.UserEntity;
import com.practice.api.exception.ResourceNotFoundException;
import com.practice.api.repository.UserEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntityResponse save(UserEntityRequest userEntity) {
        return toResponse(userEntityRepository.save(toUser(userEntity)));
    }

    @Override
    public Page<UserEntityResponse> findAll(String username, Pageable pageable) {
        if (username == null) {
            return userEntityRepository.findAll(pageable).map(this::toResponse);
        } else {
            return userEntityRepository.findByUsernameContainingIgnoreCase(username, pageable).map(this::toResponse);
        }
    }

    @Override
    public UserEntity findEntityById(Long id) {
        return getUserOrThrow(id);
    }

    @Override
    public UserEntityResponse findById(Long id) {
        return toResponse(getUserOrThrow(id));
    }

    @Override
    public UserEntityResponse updateUser(Long id, UserEntityRequest userEntityRequest) {
        UserEntity userEntity = getUserOrThrow(id);
        userEntity.setName(userEntityRequest.name());
        userEntity.setLastName(userEntityRequest.lastName());
        userEntity.setUsername(userEntityRequest.username());
        userEntity.setPassword(passwordEncoder.encode(userEntityRequest.password()));
        return toResponse(userEntityRepository.save(userEntity));
    }

    @Override
    public void delete(Long id) {
        UserEntity userEntity = getUserOrThrow(id);
        userEntityRepository.delete(userEntity);
    }

    @Override
    public UserEntityResponse updateLastName(Long id, String lastName) {
        UserEntity userEntity = getUserOrThrow(id);
        userEntity.setLastName(lastName);
        return toResponse(userEntityRepository.save(userEntity));
    }

    private UserEntity toUser(UserEntityRequest userEntityRequest) {
        UserEntity user = new UserEntity();
        user.setName(userEntityRequest.name());
        user.setLastName(userEntityRequest.lastName());
        user.setUsername(userEntityRequest.username());
        user.setPassword(passwordEncoder.encode(userEntityRequest.password()));
        return user;
    }

    private UserEntityResponse toResponse(UserEntity userEntity) {
        return new UserEntityResponse(userEntity.getName(), userEntity.getLastName(), userEntity.getUsername());
    }

    private UserEntity getUserOrThrow(Long id) {
        return userEntityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }
}
