package org.example.presenters;

import org.example.File;
import org.example.LogEntry;

import java.io.BufferedWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class CsvPresenter implements Presenter {

    public void present(File file, ArrayList<LogEntry> entries) {
        HashMap<String, ArrayList<LogEntry>> groupedByDate = (HashMap<String, ArrayList<LogEntry>>) groupByDate(entries);

        String logFileName = file.getPath().getFileName().toString();
        Path path = Paths.get("src/main/resources/avg_" + logFileName);
        try(BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
            // todo: replace by stream API
            for (Map.Entry<String,ArrayList<LogEntry>> entry : groupedByDate.entrySet())
            {
                entry.getValue().sort(new CsvPresenter.LogEntryComparator());
                writer.write(entry.getKey() + System.lineSeparator());
                for (LogEntry entryItem: entry.getValue()) {
                    writer.write(entryItem.user + "," +entryItem.website + "," + entryItem.duration + System.lineSeparator());
                }
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private Map<String, ArrayList<LogEntry>> groupByDate(ArrayList<LogEntry> entries) {
        HashMap<String, ArrayList<LogEntry>> groupedByDate = new HashMap<>();
        for (LogEntry entry : entries) {
            if (groupedByDate.containsKey(entry.dateString)) {
                ArrayList<LogEntry> logEntries = groupedByDate.get(entry.dateString);
                logEntries.add(entry);
            } else {
                ArrayList<LogEntry> logEntries = new ArrayList<>();
                logEntries.add(entry);
                groupedByDate.put(entry.dateString, logEntries);
            }
        }

        return groupedByDate;
    }

    private static class LogEntryComparator implements Comparator<LogEntry> {
        @Override
        public int compare(LogEntry o1, LogEntry o2) {
            int id1, id2;
            id1 = Integer.parseInt(o1.user.substring(4));
            id2 = Integer.parseInt(o2.user.substring(4));

            return Integer.compare(id1, id2);
        }
    }
}
