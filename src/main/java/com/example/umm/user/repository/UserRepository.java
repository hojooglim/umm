package com.example.umm.user.repository;

import com.example.umm.user.dto.DailySignupCountDTO;
import com.example.umm.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByKakaoId(Long kakaoId);

    Optional<User> findByNaverId(Long naverId);

    @Query("SELECT NEW com.example.umm.user.dto.DailySignupCountDTO(u.createdAt, COUNT(u)) " +
            "FROM User u " +
            "GROUP BY u.createdAt " +
            "ORDER BY u.createdAt")
    List<DailySignupCountDTO> getDailySignupCount();
}
