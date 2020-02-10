package org.example;

import org.example.presenters.CsvPresenter;
import org.example.presenters.Presenter;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * The first iteration will only react on new file created
 * Will only print parsed average results to the console
 */
public class Directory {
    private final Path path;
    private final Presenter presenter;

    public Directory(Path path) {
        this.path = path;
        this.presenter = new CsvPresenter();
    }

    // TODO: add threads pool
    // TODO: check only csv files
    // TODO: validate file structure
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
                    System.out.println(
                            "Event kind:" + event.kind()
                                    + ". File affected: " + event.context() + ".");
                }
                key.reset();
            }

        } catch (IOException | InterruptedException x) {
            System.err.println(Arrays.toString(x.getStackTrace()));
        }
    }

    // Just draft method to give an idea how to handle files in different threads.
    // Most probably I will rethink this as replace the implementation by a Thread pool.
    private void handle(Path pathToLogFile) {
        Runnable handler = new Handler(pathToLogFile, presenter);
        Thread t = new Thread(handler);
        t.start();
    }

    private static class Handler implements Runnable {

        protected Path pathToLogFile;
        protected Presenter presenter;

        public Handler(Path pathToLogFile, Presenter presenter) {
            this.pathToLogFile = pathToLogFile;
            this.presenter = presenter;
        }

        @Override
        public void run() {
            File file = new File(pathToLogFile);
            ArrayList<LogEntry> logEntries = file.parse();
            presenter.present(file, logEntries);
        }
    }
}
