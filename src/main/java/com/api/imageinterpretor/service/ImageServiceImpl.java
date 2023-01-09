package com.api.imageinterpretor.service;

import com.api.imageinterpretor.model.Image;
import com.api.imageinterpretor.model.repository.ImageRepo;
import com.api.imageinterpretor.service.interfaces.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final ImageRepo imageRepo;
    private final FlowServiceImpl flowService;

    @Override
    public void saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        String encodedMime = Base64.getMimeEncoder().encodeToString(file.getBytes());

        image.setBase64(encodedMime);
        try {
            Image imageFromDb = imageRepo.save(image);

            flowService.initFlow(imageFromDb);

        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
