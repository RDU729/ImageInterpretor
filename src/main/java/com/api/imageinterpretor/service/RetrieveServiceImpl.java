package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.model.Flow;
import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.FlowRepo;
import com.api.imageinterpretor.model.repository.ImageRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.api.imageinterpretor.exception.ErrorCodes.IMAGE_NOT_FOUND;
import static com.api.imageinterpretor.exception.ErrorCodes.OPTIONAL_FOUND_EMPTY;
import static com.api.imageinterpretor.service.utils.ServiceUtils.getUser;

@Slf4j
@RequiredArgsConstructor
@Service
public class RetrieveServiceImpl {
    private final FlowRepo flowRepo;
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;


    public List<Long> getAllByAUser() {
        User user = getCurrentUser();

        List<Flow> all = flowRepo.findAllByUser(user);
        List<Long> imageList = new ArrayList<>();
        for (var e : all) {
            Long id = e.getImage().getId();
            imageList.add(id);
        }
        return imageList;
    }


    public InputStream getImage(Long id) {
        List<Long> listOfImagesForCurrentUser = getAllByAUser();
        if (listOfImagesForCurrentUser.contains(id)) {
            Optional<Image> imageOptional = imageRepo.findById(id);

            if (imageOptional.isPresent()) {
                Image image = imageOptional.get();

                byte[] base64 = image.getBase64();

                return new ByteArrayInputStream(base64);
            } else {
                throw new ServiceException(OPTIONAL_FOUND_EMPTY);
            }
        } else {
            log.info("Image could not be found !! ");
            throw new ServiceException(IMAGE_NOT_FOUND);
        }
    }

    private User getCurrentUser() {
        return getUser(userRepo);
    }
}
