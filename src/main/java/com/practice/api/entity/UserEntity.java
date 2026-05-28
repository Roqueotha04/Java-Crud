package com.practice.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {

    public UserEntity(Long id, String name, String username, String password, String lastName) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.lastName = lastName;
        this.isCredentialsNonExpired=true;
        this.isAccountNonExpired=true;
        this.isAccountNonLocked=true;
        this.isEnabled=true;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    private String lastName;

    private boolean isEnabled;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;


    @OneToMany(mappedBy = "userEntity")
    private List<Post> post = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_x_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roleEntitySet = new HashSet<>();

}
