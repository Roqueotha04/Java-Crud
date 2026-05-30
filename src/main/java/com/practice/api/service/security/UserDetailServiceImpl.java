package com.practice.api.service.security;

import com.practice.api.dto.security.AuthCreateUser;
import com.practice.api.dto.security.AuthLoginRequest;
import com.practice.api.dto.security.AuthResponse;
import com.practice.api.entity.RoleEntity;
import com.practice.api.entity.UserEntity;
import com.practice.api.repository.UserEntityRepository;
import com.practice.api.utils.JwtUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    public UserDetailServiceImpl(UserEntityRepository userEntityRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userEntity.getRoleEntitySet().forEach(role ->
                authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name())));

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList
        );
    }

    public AuthResponse loginUser (AuthLoginRequest authLoginRequest){
        Authentication authentication = this.authenticate (authLoginRequest.username(), authLoginRequest.password());

        String accessToken= jwtUtils.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(authLoginRequest.username(), "User logged succesfully", accessToken, true);
        return authResponse;
    }

    public AuthResponse signUp(AuthCreateUser authCreateUser){
        Optional<UserEntity> userEntity = userEntityRepository.findByUsername(authCreateUser.username());
        if(userEntity.isPresent()){
            throw new BadCredentialsException("Username already in use");
        }
        UserEntity user = new UserEntity(authCreateUser.name(), authCreateUser.username(), passwordEncoder.encode(authCreateUser.password()), authCreateUser.lastname());
        user.setRoleEntitySet(Set.of(new RoleEntity(authCreateUser.roleEnum())));
        UserEntity newUser = userEntityRepository.save(user);
        List<SimpleGrantedAuthority> authorityList = newUser.getRoleEntitySet().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()))
                .toList();

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getUsername(), newUser.getPassword(), authorityList);

        String accessToken = jwtUtils.generateToken(authentication);
        return new AuthResponse(newUser.getName(), "User registered succesfully", accessToken, true);
    }

    public Authentication authenticate (String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
