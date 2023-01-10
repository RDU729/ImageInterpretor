package com.api.imageinterpretor.model.repository;

import com.api.imageinterpretor.model.Flow;
import com.api.imageinterpretor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowRepo extends JpaRepository<Flow, Long> {

    List<Flow> findAllByUser(User user);

}
