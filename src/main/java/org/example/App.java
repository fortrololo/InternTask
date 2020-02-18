package org.example;

import org.example.presenters.CsvPresenter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class App {

    private static final String WATCH_DIRECTORY_KEY = "watch_dir";
    private static final String OUTPUT_DIRECTORY_KEY = "output_dir";
    private static final int THREADS_COUNT = 10;

    public static void main(String[] args) {

        Properties properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(App.class.getClassLoader().getResourceAsStream("config.properties")));
            String watchDirPath = properties.get(WATCH_DIRECTORY_KEY).toString();
            String outputDirPath = properties.get(OUTPUT_DIRECTORY_KEY).toString();

            Path watchDir = Paths.get(watchDirPath);
            Path outputDir = Paths.get(outputDirPath);

            if (Files.exists(watchDir) && Files.exists(outputDir)) {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS_COUNT);
                Directory directory = new Directory(watchDir, new CsvPresenter(outputDir), threadPoolExecutor);
                directory.monitor();
            } else {
                System.out.println("Invalid paths");
                System.exit(1);
            }
        } catch (IOException x) {
            x.printStackTrace();
            System.exit(1);
        }
    }
}
