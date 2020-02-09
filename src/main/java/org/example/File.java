package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class File {

    Path path;

    public File(Path path) {
        this.path = path;
    }

    public ArrayList<LogEntry> parse() {
        ArrayList<LogEntry> entries = new ArrayList<>();
        try {
            InputStream in = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String lineContent;
            while ((lineContent = reader.readLine()) != null) {
                Line line = new Line(lineContent);
                entries.addAll(line.parse());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entries;
    }

    public Path getPath() {
        return path;
    }
}
