package com.api.imageinterpretor.service;

import com.api.imageinterpretor.model.Flow;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.FlowRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
class FlowServiceImplTest {

    EasyRandom easyRandom = new EasyRandom();

    @Autowired
    FlowRepo flowRepo;

    @Autowired
    UserRepo userRepo;

    @Test
    void create_flow() {
        Flow flow = new Flow();

        User user = new User();
        user.setName("tst");
        user.setEmail("tst");
        user.setPassword("pass");
        user.setEnabled(1);
        user.setActivationCode("activation");
        userRepo.save(user);

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        flow.setPid(String.valueOf(UUID.randomUUID()));
        flow.setUser(user);
        flow.setImgId(1L);
        flow.setCreationDate(timestamp.toLocalDateTime());
        flow.setLastUpdate(timestamp.toLocalDateTime());

        flowRepo.save(flow);
    }
}