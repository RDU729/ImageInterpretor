package com.api.imageinterpretor.utils;

import com.api.imageinterpretor.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

    public static User buildUser() {
        User user = new User();
        user.setName("Radu");
        user.setEmail("radu@gmail.com");
        user.setPassword("password");
        user.setActivationCode("activationCode");
        user.setFlow(null);
        user.setEnabled(0);
        return user;
    }
}
