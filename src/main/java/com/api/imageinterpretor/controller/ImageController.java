package com.api.imageinterpretor.controller;

import com.api.imageinterpretor.service.EmailServiceImpl;
import com.api.imageinterpretor.service.ImageServiceImpl;
import com.api.imageinterpretor.service.RetriveServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
//@AllArgsConstructor
public class ImageController {
    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private RetriveServiceImpl retriveService;

    @PostMapping(value = "/sendPhoto")
    public ResponseEntity<InputStreamResource> processRegister(@RequestParam("imagefile") MultipartFile imagefile) throws IOException {
        imageService.saveImage(imagefile);
        InputStream inputStream = imagefile.getInputStream();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(inputStream));
    }

    @GetMapping(value = "/hi")
    public List<Long> sayHi() {
        List<Long> allByAUse = retriveService.getAllByAUser();
        return allByAUse;
    }
}
