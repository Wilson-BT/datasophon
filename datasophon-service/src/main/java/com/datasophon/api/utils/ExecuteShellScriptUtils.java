package com.datasophon.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecuteShellScriptUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ExecuteShellScriptUtils.class);
    public static int executeShellScript(List<String> params) throws IOException, InterruptedException {
        logger.info("start executeShellScript:{}", params);
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(params);
        pb.redirectErrorStream(true);
        // pb.inheritIO();
        Process p = pb.start();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            logger.info("Shell Command result: {}.", line);
        }
        return p.waitFor();
    }

    public static void main(String[] args) {
        try {
            int i = executeShellScript(Arrays.asList("cmd", "dir","C:\\Users\\wangchunshun\\Desktop\\Document\\datasophon"));
            System.out.println(i);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
