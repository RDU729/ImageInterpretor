package com.api.imageinterpretor.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void saveImage(MultipartFile file) throws IOException;
}
