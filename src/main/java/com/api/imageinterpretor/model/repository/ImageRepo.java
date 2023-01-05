package com.api.imageinterpretor.model.repository;

import com.api.imageinterpretor.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Long> {

}
