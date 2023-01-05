package com.api.imageinterpretor.model.repository;

import com.api.imageinterpretor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@Param("email") String email);
}
