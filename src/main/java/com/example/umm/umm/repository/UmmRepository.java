package com.example.umm.umm.repository;

import com.example.umm.umm.entity.Umm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UmmRepository extends JpaRepository<Umm, Long> {
}
