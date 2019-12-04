package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LogParser {

    public Map<String, ArrayList<LogEntry>> parse(Path file) {
        HashMap<String, ArrayList<LogEntry>> entries = new HashMap<>();
        try {
            InputStream in = Files.newInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                ArrayList<LogEntry> newEntries = this.parseLine(line);
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

    private ArrayList<LogEntry> parseLine(String line) {
        ArrayList<LogEntry> newEntries = new ArrayList<>();
        String[] parts = line.split(",");
        int unixTimestamp = Integer.parseInt(parts[0]);
        String user = parts[1];
        String website = parts[2];
        long duration = Integer.parseInt(parts[3]);

        Date date1 = new Date((long)unixTimestamp*1000);
        LocalDateTime startDate = date1.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        Date date2 = new Date((unixTimestamp+duration)*1000);
        LocalDateTime endDate = date2.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        if (startDate.getDayOfMonth() == endDate.getDayOfMonth()) {
            Duration onFirstDay = Duration.between( LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(23, 59)), startDate);
            LogEntry entry = new LogEntry();
            entry.dateString = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase();
            entry.user = user;
            entry.duration = onFirstDay.toSeconds();
            entry.website = website;
            newEntries.add(entry);

        } else {
            Duration onFirstDay = Duration.between( LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(23, 59)), startDate);
            LogEntry entry = new LogEntry();
            entry.dateString = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase();
            entry.user = user;
            entry.duration = onFirstDay.toSeconds();
            entry.website = website;
            newEntries.add(entry);
            Duration onSecondDay = Duration.between(LocalDateTime.of(endDate.toLocalDate(), LocalTime.of(0, 0)), endDate);
            LogEntry entry2 = new LogEntry();
            entry2.dateString = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase();
            entry2.user = user;
            entry2.duration = onSecondDay.toSeconds();
            entry2.website = website;
            newEntries.add(entry2);
        }

        return newEntries;
    }
}
