package com.example.CyProject.shopping.model.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {

    List<ItemEntity> findAllByOrderByRdtDesc();
    List<ItemEntity> findAllByOrderByRdtAsc();
    List<ItemEntity> findAllByOrderByPriceDesc();
    List<ItemEntity> findAllByOrderByPriceAsc();

    List<ItemEntity> findByNmContainsOrderByRdtDesc(String search);

    @Query("SELECT i FROM ItemEntity as i WHERE i.icategory = :category and i.nm like concat ('%',:search,'%') order by i.rdt desc")
    List<ItemEntity> searchByCategoryAndTxtByOrderByRdtDesc(int category, String search);

    @Query("SELECT i FROM ItemEntity as i WHERE i.icategory = :category and i.nm like concat ('%',:search,'%') order by i.rdt asc")
    List<ItemEntity> searchByCategoryAndTxtByOrderByRdtAsc(int category, String search);

    @Query("SELECT i FROM ItemEntity as i WHERE i.icategory = :category and i.nm like concat ('%',:search,'%') order by i.price desc")
    List<ItemEntity> searchByCategoryAndTxtByOrderByPriceDesc(int category, String search);

    @Query("SELECT i FROM ItemEntity as i WHERE i.icategory = :category and i.nm like concat ('%',:search,'%') order by i.price asc")
    List<ItemEntity> searchByCategoryAndTxtByOrderByPriceAsc(int category, String search);

}
