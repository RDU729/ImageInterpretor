package com.api.imageinterpretor.model.repository;

import com.api.imageinterpretor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByActivationCode(String activationCode);

}
