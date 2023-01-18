package com.api.imageinterpretor.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
class PythonServiceTest {

    @InjectMocks
    PythonService pythonService;

    @Test
    void tst() throws IOException, ExecutionException, InterruptedException {
        pythonService.runPy();
    }
}