package com.example.CyProject.home.model.comment;

import com.example.CyProject.home.HomeCategory;
import com.example.CyProject.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    List<CommentEntity> findAllByIhostAndIboardAndCategoryAndParent(UserEntity ihost, int iboard, int category, int parent);

    @Query("SELECT c from CommentEntity as c where c.category = :icategory and c.parent = :icmt and c.ihost = :ihost")
    List<CommentEntity> selReplyList(int icmt, UserEntity ihost, int icategory);
}