package com.api.imageinterpretor.service;

import com.api.imageinterpretor.model.Flow;
import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.FlowRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FlowServiceImpl {

    private final FlowRepo flowRepo;
    private final UserRepo userRepo;

    public void initFlow(Image image) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        Optional<User> userOptional = userRepo.findByEmail(currentPrincipalName);
        User user = userOptional.get();

        log.info(currentPrincipalName);
        Flow flow = new Flow();

        flow.setPid(String.valueOf(UUID.randomUUID()));
        flow.setUser(user);
        flow.setImage(image);
//        flow.setLastUpdate(String.valueOf(LocalDateTime.now()));
//        flow.setCreationDate(String.valueOf(LocalDateTime.now()));
        flow.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
        flow.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

        flowRepo.save(flow);
    }
}
