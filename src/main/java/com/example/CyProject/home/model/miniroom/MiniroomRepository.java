package com.example.CyProject.home.model.miniroom;

import com.example.CyProject.shopping.model.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MiniroomRepository extends JpaRepository<MiniroomEntity, Integer> {
    long countByIhostAndMyroom(int ihost, ItemEntity myroom);

    List<MiniroomEntity> findAllByIhost(int ihost);

    long countByIhostAndRepre(int ihost, boolean repre);
    MiniroomEntity findByIhostAndRepre(int ihost, boolean repre);

    @Query("SELECT m FROM MiniroomEntity m WHERE m.ihost = :ihost AND m.myroom.item_id = :item_id")
    MiniroomEntity findByItemIdInMyroom(int ihost, int item_id);
}
