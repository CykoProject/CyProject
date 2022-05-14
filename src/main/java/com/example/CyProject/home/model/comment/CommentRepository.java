package com.example.CyProject.home.model.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    @Query("SELECT COUNT (c) FROM CommentEntity c WHERE (c.iboard = :iboard AND c.category = :category) AND c.parent = 0")
    int selCommentWithOutReplyCnt(int iboard, int category);

    @Query("SELECT c FROM CommentEntity c WHERE (c.iboard = :iboard AND c.category = :category) AND c.parent = 0")
    List<CommentEntity> selCommentWithOutReply(int iboard, int category, Pageable pageable);
}