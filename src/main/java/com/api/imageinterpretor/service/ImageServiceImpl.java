package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.repository.ImageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.api.imageinterpretor.exception.ErrorCodes.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl {
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
        try (InputStream input = file.getInputStream()) {
            try {
                ImageIO.read(input).toString();
            } catch (Exception e) {
                throw new ServiceException(UNSUPPORTED_FILE_TYPE);
            }
        }
    }
}
