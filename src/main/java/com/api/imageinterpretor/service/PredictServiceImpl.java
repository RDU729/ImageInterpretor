package com.api.imageinterpretor.service;

import com.api.imageinterpretor.controller.exception.ServiceException;
import com.api.imageinterpretor.service.interfaces.PredictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.api.imageinterpretor.exception.ErrorCodes.COULD_NOT_READ_WRITE_FILE_FOR_PREDICTION;
import static com.api.imageinterpretor.utils.Constants.TEMP_FILE_LOCATION;

@Slf4j
@Service
@RequiredArgsConstructor
public class PredictServiceImpl implements PredictService {

    private final PythonServiceImpl pythonServiceImpl;

    private final RetrieveServiceImpl retrieveService;

    @Override
    public String predict(Long id) {
        InputStream file = retrieveService.getImage(id);
        try {
            Image image = ImageIO.read(file);
            ImageIO.write((RenderedImage) image, "jpg", new File(TEMP_FILE_LOCATION));
        } catch (IOException e) {
            throw new ServiceException(COULD_NOT_READ_WRITE_FILE_FOR_PREDICTION);
        }
        String s = pythonServiceImpl.runPy();
        File toBeDeleted = new File(TEMP_FILE_LOCATION);
        boolean delete = toBeDeleted.delete();
        if (!delete) {
            log.info("File could not de deleted");
        }
        return s;
    }
}
