package com.api.imageinterpretor.controller;

import com.api.imageinterpretor.dto.SignUpDTO;
import com.api.imageinterpretor.model.repository.ImageRepo;
import com.api.imageinterpretor.service.ImageServiceImpl;
import com.api.imageinterpretor.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@AllArgsConstructor
public class ImageController {

    private ImageServiceImpl imageService;

    @PostMapping(value = "/sendPhoto")
    public ResponseEntity<InputStreamResource> processRegister( @RequestParam("imagefile") MultipartFile imagefile) throws IOException {
        imageService.saveImage(imagefile);
        InputStream inputStream = imagefile.getInputStream();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(inputStream));
    }
}
