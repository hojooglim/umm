package com.example.umm.follow.repository;

import com.example.umm.follow.entity.Follow;
import com.example.umm.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    Follow findByFollowingUserAndUser(User followingUser, User user);
}
