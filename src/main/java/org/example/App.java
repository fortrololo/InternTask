package org.example;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Provide a path to the logs directory");
            System.exit(0);
        }

        Path path = Paths.get(args[0]);
        try {
            Path fp = path.toRealPath();

            Directory directory = new Directory(path);
            directory.monitor();
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
            // Logic for case when file doesn't exist.
        } catch (IOException x) {
            System.err.format("%s%n", x);
            // Logic for other sort of file error.
        }

    }

}
