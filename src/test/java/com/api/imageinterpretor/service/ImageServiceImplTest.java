package com.api.imageinterpretor.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.model.repository.ImageRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ImageServiceImplTest {

    private final String FROM = "src/test/java/com/api/imageinterpretor/utils/bsh.jpg";
    private final String RUFUS = "src/test/java/com/api/imageinterpretor/utils/rufus.jpg";

    private final Logger logger = (Logger) LoggerFactory.getLogger(EmailServiceImpl.class);

    @Captor
    public ArgumentCaptor<LoggingEvent> captorLoggingEvent;
    @Mock
    ImageRepo imageRepo;
    @Mock
    FlowServiceImpl flowService;
    @InjectMocks
    ImageServiceImpl imageService;
    @Mock
    private Appender<ILoggingEvent> mockAppender;

    @BeforeEach
    void setUp() {
        logger.addAppender(mockAppender);
        logger.setLevel(Level.ALL);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("given an image should convert to imput stream and save to db")
    void should_save_image() throws IOException {
        //Given
        InputStream in = new FileInputStream(FROM);
        MockMultipartFile multipartFile1 = new MockMultipartFile("image1", "image1", "image/png", in);

        //When
        imageService.saveImage(multipartFile1);

        //Then
//        verify(mockAppender, times(3)).doAppend(captorLoggingEvent.capture());
//        List<LoggingEvent> loggingEvents = captorLoggingEvent.getAllValues();
//
//        Assertions.assertThat(loggingEvents.get(1).getLevel()).isEqualTo(Level.INFO);
//        Assertions.assertThat(loggingEvents.get(1).getFormattedMessage()).isEqualTo("Mail sent successfully");

    }

    @Test
    @DisplayName("given an wrong file type should throw error")
    void should_throw_error_when_not_given_an_image() {
        //Given
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());

        //When
        ServiceException exception = assertThrows(ServiceException.class, () -> {
            imageService.saveImage(file);
        });

        //Then
        assertThat(exception.getMessage())
                .isEqualTo("Wrong file format uploaded");
    }

    //@Test
    @DisplayName("Should disable user after uploading 3 harmful files")
    @WithMockUser(username = "sa2@sa", password = "sa", authorities = {"ADMIN", "USER"})
    void should_disable_user() throws IOException {
        //Given
        InputStream in = new FileInputStream(RUFUS);
        MockMultipartFile multipartFile1 = new MockMultipartFile("rufus", "rufus", "application/json", in);

        //When
        imageService.saveImage(multipartFile1);
    }

}

