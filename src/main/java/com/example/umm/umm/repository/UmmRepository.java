package com.example.umm.umm.repository;

import com.example.umm.umm.entity.Umm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UmmRepository extends JpaRepository<Umm,Long> {
    List<Umm> findAllByOrderByModifiedAtDesc();

//    Slice<Umm> findSliceByPrice(int price, Pageable pageable);

}
