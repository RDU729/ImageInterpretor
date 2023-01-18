package com.api.imageinterpretor.model.repository;

import com.api.imageinterpretor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM utilizatori WHERE DISABLE_DATE > SYSDATE - 1/1440", nativeQuery = true)
    List<User> findOlderThat1Min();
}
