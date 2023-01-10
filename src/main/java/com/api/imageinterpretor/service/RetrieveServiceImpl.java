package com.api.imageinterpretor.service;

import com.api.imageinterpretor.model.Flow;
import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.FlowRepo;
import com.api.imageinterpretor.model.repository.ImageRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RetrieveServiceImpl {
    private final FlowRepo flowRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;
    private final FlowServiceImpl flowService;


    public List<Long> getAllByAUser() {
        User user = getCurrentUser();

        List<Flow> all = flowRepo.findAllByUser(user);
        List<Long> imageList = new ArrayList<>();
        for(var e : all){
            Long id = e.getImage().getId();
            imageList.add(id);
        }
        return imageList;
    }


    public InputStream getImage(Long id) throws IOException {

        List<Long> listOfImagesForCurrentUser = getAllByAUser();
        if (listOfImagesForCurrentUser.contains(id)){
            Optional<Image> imageOptional = imageRepo.findById(id);
            Image image = imageOptional.get();

            byte[] base64 = image.getBase64();

            InputStream inputStream = new ByteArrayInputStream(base64);

            return inputStream;
        }else {
            log.info("Image could not be found !! ");
            throw new RuntimeException();
        }

    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getPrincipal();
        String currentPrincipalName = authentication.getName();
        User user = userRepo.findByEmail(currentPrincipalName).get();
        return user;
    }

}
