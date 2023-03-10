package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.model.Flow;
import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.FlowRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import com.api.imageinterpretor.service.interfaces.FlowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.api.imageinterpretor.exception.ErrorCodes.OPTIONAL_FOUND_EMPTY;

@Slf4j
@RequiredArgsConstructor
@Service
public class FlowServiceImpl implements FlowService {

    private final FlowRepo flowRepo;
    private final UserRepo userRepo;

    @Override
    public void initFlow(Image image) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        String currentPrincipalName = authentication.getName();

        Optional<User> userOptional = userRepo.findByEmail(currentPrincipalName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            log.info(currentPrincipalName);
            Flow flow = new Flow();

            flow.setPid(String.valueOf(UUID.randomUUID()));
            flow.setUser(user);
            flow.setImage(image);
            flow.setLastUpdate(Timestamp.valueOf(LocalDateTime.now()));
            flow.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));

            flowRepo.save(flow);
        } else {
            throw new ServiceException(OPTIONAL_FOUND_EMPTY);
        }
    }
}
