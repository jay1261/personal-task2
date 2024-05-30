package com.sparta.spartascheduler.repository;

import com.sparta.spartascheduler.entitiy.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
