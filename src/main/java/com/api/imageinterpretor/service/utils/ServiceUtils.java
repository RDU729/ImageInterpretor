package com.api.imageinterpretor.service.utils;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static com.api.imageinterpretor.exception.ErrorCodes.OPTIONAL_FOUND_EMPTY;

@UtilityClass
public class ServiceUtils {
    public static User getUser(UserRepo userRepo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        String currentPrincipalName = authentication.getName();
        Optional<User> userOptional = userRepo.findByEmail(currentPrincipalName);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ServiceException(OPTIONAL_FOUND_EMPTY);
        }
    }
}
