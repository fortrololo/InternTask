package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class LogMonitor {
    private String pathString;

    LogMonitor(String pathString) {
        this.pathString = pathString;
    }

    public void run() {
        LogParser parser = new LogParser();
        Map result = parser.parse(Paths.get(pathString));
        result.keySet();
        int a = 1;
    }

//    public void monitor() {
//        Path path = Paths.get(pathString);
//        try {
//            WatchService watcher = FileSystems.getDefault().newWatchService();
//
//            path.register(watcher,
//                    ENTRY_CREATE,
//                    ENTRY_DELETE,
//                    ENTRY_MODIFY);
//
//            WatchKey key;
//            while ((key = watcher.take()) != null) {
//                for (WatchEvent<?> event : key.pollEvents()) {
//                    System.out.println(
//                            "Event kind:" + event.kind()
//                                    + ". File affected: " + event.context() + ".");
//                }
//                key.reset();
//            }
//
//        } catch (IOException | InterruptedException x) {
//            System.err.println(Arrays.toString(x.getStackTrace()));
//        }
//    }

}
