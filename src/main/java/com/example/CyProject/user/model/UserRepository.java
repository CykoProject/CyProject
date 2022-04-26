package com.example.CyProject.user.model;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);
    UserEntity findByIuser(int iuser);
    UserEntity findByCellphone(String cellphone);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity set upw = ?1 WHERE email = ?2")
    int updUserUpw(String upw, String email);

    List<UserEntity> findByNm(String search);

}