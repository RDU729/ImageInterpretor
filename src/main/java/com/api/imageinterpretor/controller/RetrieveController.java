package com.api.imageinterpretor.controller;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.service.RetrieveServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RetrieveController {

    @Autowired
    RetrieveServiceImpl retrieveService;

    @RequestMapping(path = "retrieve/all")
    public ResponseEntity<List<Long>> retrieveAll() {
        List<Long> allByAUser = retrieveService.getAllByAUser();
        return ResponseEntity.ok(allByAUser);
    }

    @RequestMapping(path = "retrieve/{path}")
    public ResponseEntity<InputStreamResource> retrieveOne(@PathVariable("path") Long path) throws IOException, ServiceException {
        InputStream image = retrieveService.getImage(path);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(image));
    }
}
