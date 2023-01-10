package com.api.imageinterpretor.service;

import com.api.imageinterpretor.model.Flow;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.FlowRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RetriveServiceImpl {
    private final FlowRepo flowRepo;
    private final UserRepo userRepo;


    public List<Long> getAllByAUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        String currentPrincipalName = authentication.getName();
        User user = userRepo.findByEmail(currentPrincipalName).get();

        List<Flow> all = flowRepo.findAllByUser(user);
        List<Long> imageList = new ArrayList<>();
        for(var e : all){
            Long id = e.getImage().getId();
            imageList.add(id);
        }
        return imageList;
    }
}
