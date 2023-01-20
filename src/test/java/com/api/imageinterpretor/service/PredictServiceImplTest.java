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
class PredictServiceImplTest {

    private final String FROM = "src/test/java/com/api/imageinterpretor/utils/bsh.jpg";
    private final String TO = "src/main/resources/pyImages/d.jpg";

    @Mock
    PythonServiceImpl pythonServiceImpl;

    @Mock
    RetrieveServiceImpl retrieveService;

    PredictServiceImpl predictServiceImpl;

    @BeforeEach
    void setUp() {
        predictServiceImpl = new PredictServiceImpl(pythonServiceImpl, retrieveService);
    }

    @Test
    @DisplayName("Given image id should predict using python algorithm")
    void test_predict_successful() throws IOException {
        //Given

        InputStream in = new FileInputStream(FROM);

        when(retrieveService.getImage(anyLong())).thenReturn(in);

        //When
        predictServiceImpl.predict(1L);

        //Then
    }
}