package com.api.imageinterpretor.service.scheguled;

import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledService {

    private final UserRepo userRepo;

    @Scheduled(cron = "1 * * * * *")
    private void reactivateUsers() {
        List<User> olderThat1Min = userRepo.findOlderThat1Min();
        log.info("running scheduled task");
        olderThat1Min.forEach(user -> {
            user.setEnabled(1);
            userRepo.save(user);
            log.info("Enabling : {}", user.toString());
        });
    }
}
