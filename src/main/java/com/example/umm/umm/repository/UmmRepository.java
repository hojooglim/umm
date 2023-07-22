package com.example.umm.umm.repository;

import com.example.umm.umm.entity.Umm;
import com.example.umm.user.dto.DailyUmmCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UmmRepository extends JpaRepository<Umm,Long> {
    List<Umm> findAllByOrderByCreatedAtDesc();

    @Query("SELECT NEW com.example.umm.user.dto.DailyUmmCountDTO(u.createdAt, COUNT(u)) " +
            "FROM Umm u " +
            "GROUP BY u.createdAt " +
            "ORDER BY u.createdAt")
    List<DailyUmmCountDTO> getDailyUmmCount();
}
