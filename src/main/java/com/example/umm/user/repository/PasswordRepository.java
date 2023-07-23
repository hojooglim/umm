package com.example.umm.user.repository;

import com.example.umm.user.entity.Password;
import com.example.umm.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Long> {

    List<Password> findAllByUser(User user);

}
