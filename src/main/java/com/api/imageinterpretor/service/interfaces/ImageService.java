package com.api.imageinterpretor.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface ImageService {

    InputStream saveImage(MultipartFile file);
}
