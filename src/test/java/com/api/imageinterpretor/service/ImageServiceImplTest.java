package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.model.repository.ImageRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    ImageRepo imageRepo;

    @Mock
    FlowServiceImpl flowService;

    @InjectMocks
    ImageServiceImpl imageService;


    //@Test
    @DisplayName("given an image should convert to imput stream and save to db")
    void should_save_image() {
        //Given
        MockMultipartFile file = new MockMultipartFile("image", "", "application/json", "{\"image\": /Users/radupopescu/Documents/GitHub/ImageInterpretor/src/test/java/com/api/imageinterpretor/utils/bsh.jpg}".getBytes());

        //When
        imageService.saveImage(file);


        //Then

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

}

