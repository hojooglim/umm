package com.example.umm.likeit.repository;


import com.example.umm.comment.entity.Comment;
import com.example.umm.likeit.entity.LikeIt;
import com.example.umm.umm.entity.Umm;
import com.example.umm.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeItRepository extends JpaRepository<LikeIt, Long> {
    Optional<LikeIt> findByUmmAndUser(Umm umm, User user);

    Optional<LikeIt> findByCommentAndUser(Comment comment, User user);


}
