package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Line {
    String content;

    public Line(String content) {
        this.content = content;
    }

    public List<LogEntry> parse() {
        ArrayList<LogEntry> newEntries = new ArrayList<>();
        String[] parts = content.split(",");
        int unixTimestamp = Integer.parseInt(parts[0]);
        String user = parts[1];
        String website = parts[2];
        long duration = Integer.parseInt(parts[3]);

        Date date1 = new Date((long)unixTimestamp*1000);
        LocalDateTime startDate = date1.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        Date date2 = new Date((unixTimestamp+duration)*1000);
        LocalDateTime endDate = date2.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();

        Period period = Period.between(startDate.toLocalDate(), endDate.toLocalDate());

        ArrayList<LocalDateTime> checkPoints = new ArrayList<>();
        checkPoints.add(startDate);
        for (int i = 1; i <= period.getDays(); i++) {
            checkPoints.add(startDate.toLocalDate().plusDays(i).atTime(0,0));
        }
        checkPoints.add(endDate);

        Iterator<LocalDateTime> iterator = checkPoints.iterator();

        LocalDateTime bufferDateTime = null;
        while (iterator.hasNext()) {
            if (bufferDateTime == null) {
                bufferDateTime = iterator.next();
            } else {
                LocalDateTime dateTime = iterator.next();
                Duration onCurrentDay = Duration.between(bufferDateTime, dateTime);
                LogEntry entry = new LogEntry();
                entry.dateString = bufferDateTime.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase();
                entry.user = user;
                entry.duration = onCurrentDay.toSeconds();
                entry.website = website;
                if (entry.duration > 0) {
                    newEntries.add(entry);
                }

                bufferDateTime = dateTime;
            }

        }

        return newEntries;
    }
}
