package com.example.CyProject.shopping.model.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {

    List<CartEntity> findAllByIuser(int iuser);

    int countAllByIuser(int iuser);
}
