package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class File {

    Path file;

    public File(Path file) {
        this.file = file;
    }

    public Map<String, ArrayList<LogEntry>> parse() {
        HashMap<String, ArrayList<LogEntry>> entries = new HashMap<>();
        try {
            InputStream in = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String lineContent;
            while ((lineContent = reader.readLine()) != null) {
                Line line = new Line(lineContent);
                List<LogEntry> newEntries = line.parse();
                for (LogEntry entry:newEntries) {
                    if (entries.containsKey(entry.dateString)) {
                        ArrayList<LogEntry> logEntries = entries.get(entry.dateString);
                        logEntries.add(entry);
                    } else {
                        ArrayList<LogEntry> logEntries = new ArrayList<>();
                        logEntries.add(entry);
                        entries.put(entry.dateString, logEntries);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return entries;
    }
}
