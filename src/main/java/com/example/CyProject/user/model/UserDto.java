package com.example.CyProject.user.model;

import com.example.CyProject.config.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String upw;
    private String email;
    private String nm;
    private Role role;

    public UserEntity toEntity() {
        UserEntity user = UserEntity.builder()
                .upw(upw)
                .nm(nm)
                .email(email)
                .role(role.USER)
                .build();
        return user;
    }
}