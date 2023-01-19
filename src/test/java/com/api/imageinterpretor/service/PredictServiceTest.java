package com.api.imageinterpretor.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class PredictServiceTest {

    private final String FROM = "src/test/java/com/api/imageinterpretor/utils/bsh.jpg";
    private final String TO = "src/main/resources/pyImages/d.jpg";

    @Mock
    PythonService pythonService;

    @Mock
    RetrieveServiceImpl retrieveService;

    PredictService predictService;

    @BeforeEach
    void setUp() {
        predictService = new PredictService(pythonService, retrieveService);
    }

    @Test
    @DisplayName("Given image id should predict using python algorithm")
    void test_predict_successful() throws IOException {
        //Given

        InputStream in = new FileInputStream(FROM);

        when(retrieveService.getImage(anyLong())).thenReturn(in);

        //When
        predictService.predict(1L);

        //Then
    }
}