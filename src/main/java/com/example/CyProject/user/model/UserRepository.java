package com.example.CyProject.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);
    UserEntity findByIuser(int iuser);
    UserEntity findByCellphone(String cellphone);
    UserEntity findByCellphoneAndEmail(String email, String cellphone);

    List<UserEntity> findByNm(String search);

}