package com.api.imageinterpretor.controller;

import com.api.imageinterpretor.service.ImageServiceImpl;
import com.api.imageinterpretor.service.PredictServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class ImageController {
    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private PredictServiceImpl predictServiceImpl;


    @PostMapping(value = "/sendPhoto")
    public ResponseEntity<InputStreamResource> processRegister(@RequestParam("imagefile") MultipartFile imagefile) throws IOException {
        InputStream inputStream1 = imageService.saveImage(imagefile);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(inputStream1));
    }

    @GetMapping(value = "/hi")
    public ResponseEntity<String> sayHi() {
        return ResponseEntity.ok("Hi");
    }

    @PostMapping("/predict/{id}")
    public ResponseEntity<String> predictImage(@PathVariable("id") Long id) throws IOException, ExecutionException, InterruptedException {
        String predict = predictServiceImpl.predict(id);
        return ResponseEntity.ok(predict);
    }
}
