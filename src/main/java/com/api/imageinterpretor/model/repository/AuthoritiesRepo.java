package com.api.imageinterpretor.model.repository;

import com.api.imageinterpretor.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthoritiesRepo extends JpaRepository<Authorities, Long> {
}
