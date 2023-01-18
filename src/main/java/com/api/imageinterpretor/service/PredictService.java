package com.api.imageinterpretor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import static com.api.imageinterpretor.utils.Constants.TEMP_FILE_LOCATION;

@Slf4j
@Service
public class PredictService {

    @Autowired
    PythonService pythonService;

    @Autowired
    RetrieveServiceImpl retrieveService;

    public String predict(Long id) throws IOException, ExecutionException, InterruptedException {
        InputStream file = retrieveService.getImage(id);
        Image image = ImageIO.read(file);
        ImageIO.write((RenderedImage) image, "jpg", new File(TEMP_FILE_LOCATION));
        String s = pythonService.runPy();
        File toBeDeleted = new File(TEMP_FILE_LOCATION);
        boolean delete = toBeDeleted.delete();
        if (!delete){
            log.info("File could not de deleted");
        }
        return s;
    }
}
