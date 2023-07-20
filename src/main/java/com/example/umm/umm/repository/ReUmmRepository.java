package com.example.umm.umm.repository;

import com.example.umm.umm.entity.ReUmm;
import com.example.umm.umm.entity.Umm;
import com.example.umm.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReUmmRepository extends JpaRepository<ReUmm,Long> {
    Optional<ReUmm> findByUmmAndUser(Umm umm, User user);
}
