package com.api.imageinterpretor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class PredictService {

    @Autowired
    PythonService pythonService;

    public String predict() throws IOException, ExecutionException, InterruptedException {
        String s = pythonService.runPy();
        return s;
    }
}
