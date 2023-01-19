package com.api.imageinterpretor.service;

import com.api.imageinterpretor.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import static com.api.imageinterpretor.utils.Constants.TEMP_FILE_LOCATION;

@ExtendWith(MockitoExtension.class)
@Slf4j
class PythonServiceTest {

    @InjectMocks
    PythonService pythonService;

    //@Test
    void tst() throws IOException {
        //Given
        File file = new File("src/test/java/com/api/imageinterpretor/utils/bsh.jpg");
        BufferedImage image = ImageIO.read(file);
        ImageIO.write( image, "jpg", new File(TEMP_FILE_LOCATION));

        File toBeDeleted = new File(TEMP_FILE_LOCATION);
        boolean delete = toBeDeleted.delete();
        //When
        String s = pythonService.runPy();



        //Then


    }

    //@Test
    void temp() {
        log.info(System.getProperty("java.io.tmpdir"));
    }
}