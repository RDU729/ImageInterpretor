package com.api.imageinterpretor.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@Slf4j
public class PythonService {

    public String runPy() throws IOException, InterruptedException, ExecutionException {
        List<String> pythonOutPut = new ArrayList<>();

        Process p2 = Runtime.getRuntime().exec("python3 main.py");
        StreamGobbler streamGobbler =
                new StreamGobbler(p2.getInputStream(), pythonOutPut);

        Future<?> future = Executors.newSingleThreadExecutor().submit(streamGobbler);

        int exitCode = p2.waitFor();

        assert exitCode == 0;

        String s = pythonOutPut.get(pythonOutPut.size() - 1);
        String[] split = s.split(":");
        log.info(split[1]);
        return split[1];
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