package com.api.imageinterpretor.service;

import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.repository.ImageRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl {
    private final ImageRepo imageRepo;
    private final FlowServiceImpl flowService;

    public InputStream saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        byte[] bytes = file.getBytes();
        InputStream targetStream = new ByteArrayInputStream(bytes);

        image.setBase64(bytes);

        try {
            Image imageFromDb = imageRepo.save(image);

            flowService.initFlow(imageFromDb);

        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return targetStream;
    }

    public InputStream getImage(Long id) throws IOException {
        Optional<Image> imageOptional = imageRepo.findById(id);
        Image image = imageOptional.get();

        byte[] base64 = image.getBase64();

        InputStream inputStream = new ByteArrayInputStream(base64);

        return inputStream;
    }
}
