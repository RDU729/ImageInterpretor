package com.api.imageinterpretor.service;

import com.api.imageinterpretor.service.interfaces.PythonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class PythonServiceImpl implements PythonService {

    @Override
    public String runPy() {
        log.info("Starting python prediction");
        List<String> pythonOutPut = new ArrayList<>();
        String finalString = "";

        try {
            Process p2 = Runtime.getRuntime().exec("python3 main.py");

            StreamGobbler streamGobbler =
                    new StreamGobbler(p2.getInputStream(), pythonOutPut);

            Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);
            try {
                int exitCode = p2.waitFor();

                assert exitCode == 0;
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }

            String s = pythonOutPut.get(pythonOutPut.size() - 1);
            String[] split = s.split(":");
            log.info(split[1]);
            finalString = split[1];
            log.info("Python script ended");
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return finalString;
    }

    @AllArgsConstructor
    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private List<String> pythonOutPut;

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(pythonOutPut::add);
        }
    }
}