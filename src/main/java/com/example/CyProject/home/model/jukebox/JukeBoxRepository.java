package com.example.CyProject.home.model.jukebox;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;

public interface JukeBoxRepository extends JpaRepository<JukeBoxEntity, Integer> {

    List<JukeBoxEntity> findAllByIhost(int ihost);

    @Query("SELECT j from JukeBoxEntity j where j.ihost=?1 and j.repre=true")
    List<JukeBoxEntity> selRepreList(int ihost);

    @Modifying
    @Transactional
    @Query("UPDATE JukeBoxEntity set repre = ?1 where ihost=?2 and ijukebox=?3")
    int updRepreStatus(boolean repre, int ihost, int ijukebox);
}
