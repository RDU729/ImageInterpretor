package com.api.imageinterpretor.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PythonServiceTest {

    @InjectMocks
    PythonService pythonService;

    @Test
    void tst() throws IOException, ExecutionException, InterruptedException {
        pythonService.runPy();
    }

    @Test
    void temp(){
       log.info(System.getProperty("java.io.tmpdir"));
    }
}