package com.example.CyProject.shopping.model.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {

    List<ItemEntity> findAllByOrderByRdtDesc();
    List<ItemEntity> findByNmContainsOrderByRdtDesc(String search);


    @Query("SELECT i FROM ItemEntity as i WHERE i.icategory = :icategory and i.nm like concat ('%',:search,'%')")
    List<ItemEntity> searchByCategoryAndTxtByOrderByRdtDesc(int icategory, String search);


}
