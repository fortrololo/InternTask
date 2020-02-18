package org.example;

import org.example.presenters.Presenter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadPoolExecutor;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * The first iteration will only react on new file created
 * Will only print parsed average results to the console
 */
public class Directory {
    private final Path path;
    private final Presenter presenter;
    private final ThreadPoolExecutor executor;

    public Directory(Path path, Presenter presenter, ThreadPoolExecutor threadPoolExecutor) {
        this.path = path;
        this.presenter = presenter;
        this.executor = threadPoolExecutor;
    }

    public void monitor() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.path)) {
            for (Path file: stream) {
                this.handle(file);
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }

        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();

            path.register(watcher,
                    ENTRY_CREATE,
                    ENTRY_DELETE,
                    ENTRY_MODIFY);

            WatchKey key;
            while ((key = watcher.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == ENTRY_CREATE) {
                        Path pathToLogFile = Paths.get(this.path.toString() + java.io.File.separator + event.context());
                        this.handle(pathToLogFile);
                    }
                }
                key.reset();
            }

        } catch (IOException | InterruptedException x) {
            System.err.println(Arrays.toString(x.getStackTrace()));
        }
    }

    private void handle(Path pathToLogFile) {
        this.executor.submit(() -> {
            if (pathToLogFile.toString().toLowerCase().endsWith("csv")) {
                File file = new File(pathToLogFile);
                ArrayList<LogEntry> logEntries = file.parse();

                presenter.present(file, logEntries);
            }
        });
    }
}
