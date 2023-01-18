package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.User;
import com.api.imageinterpretor.model.repository.ImageRepo;
import com.api.imageinterpretor.model.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static com.api.imageinterpretor.exception.ErrorCodes.*;
import static com.api.imageinterpretor.service.utils.ServiceUtils.getUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl {
    private final UserRepo userRepo;
    private final ImageRepo imageRepo;
    private final FlowServiceImpl flowService;

    public InputStream saveImage(MultipartFile file) {
        try {

            validateFileType(file);

            Image image = new Image();
            byte[] bytes = file.getBytes();
            log.info("File converted to bytes");

            InputStream targetStream = new ByteArrayInputStream(bytes);

            image.setBase64(bytes);

            try {
                Image imageFromDb = imageRepo.save(image);
                flowService.initFlow(imageFromDb);
                log.info("Image saved successfully !");
                log.info("Flow created !");

            } catch (Exception e) {
                log.info(e.getMessage());
                throw new ServiceException(IMAGE_COULD_NOT_BE_SAVED);
            }
            return targetStream;
        } catch (IOException ioException) {
            throw new ServiceException(FILE_COULD_NOT_BE_READ);
        }
    }

    private void validateFileType(MultipartFile file) throws IOException {

        Tika tika = new Tika();
        String detect = tika.detect(file.getBytes());

        log.info("Found file of the following type {} ", detect);

        if (!detect.contains("image")) {
            if (detect.contains("application") || detect.contains("stream")) {
                User currentUser = getCurrentUser();

                if (currentUser.getOffence() > 3) {
                    currentUser.setEnabled(0);
                    currentUser.setOffence(0);
                    currentUser.setDisableDate(Timestamp.valueOf(LocalDateTime.now()));
                    userRepo.save(currentUser);
                    SecurityContextHolder.getContext().setAuthentication(null);
                    throw new ServiceException(EXCEEDED_3_OFFENCES);

                } else {
                    currentUser.setOffence(currentUser.getOffence() + 1);
                    userRepo.save(currentUser);
                    throw new ServiceException(HARMFUL_FILE);
                }
            }
            throw new ServiceException(UNSUPPORTED_FILE_TYPE);
        }
    }

    private User getCurrentUser() {
        return getUser(userRepo);
    }
}
