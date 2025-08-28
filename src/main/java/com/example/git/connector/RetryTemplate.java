package com.example.git.connector;

import java.io.IOException;
import java.util.concurrent.Callable;

public class RetryTemplate {
    public static <T> T execute(Callable<T> callable) throws Exception {
        int retries = 3;
        long wait = 1000;
        while (true) {
            try {
                return callable.call();
            } catch (IOException e) {
                if (--retries == 0) throw e;
                Thread.sleep(wait);
                wait *= 2;
            }
        }
    }
}
