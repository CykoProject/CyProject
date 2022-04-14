package com.example.CyProject.user.model;

import com.example.CyProject.config.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String upw;
    private String email;
    private String nm;
    private Role role;
    private String oldUpw;
    private String newUpw;

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
