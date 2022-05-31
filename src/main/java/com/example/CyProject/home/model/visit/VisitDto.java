package com.example.CyProject.home.model.visit;

import com.example.CyProject.user.model.UserEntity;
import lombok.*;
import org.springframework.data.domain.PageRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class
VisitDto {
    private int ivisit;
    private int ihost;
    private UserEntity iuser;
    private String ctnt;
    private boolean secret;
    private String tab;

    public VisitEntity toEntity() {
        VisitEntity entity = VisitEntity.builder()
                .ivisit(ivisit)
                .ihost(ihost)
                .iuser(iuser)
                .ctnt(ctnt)
                .secret(secret)
                .build();
        return entity;
    }
}
