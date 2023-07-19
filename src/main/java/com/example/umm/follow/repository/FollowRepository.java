package com.example.umm.follow.repository;

import com.example.umm.follow.entity.Follow;
import com.example.umm.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    Optional<Follow> findByFollowingUserAndUser(User followingUser, User user);
}
