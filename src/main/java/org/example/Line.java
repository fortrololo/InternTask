package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
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
        if (startDate.getDayOfMonth() == endDate.getDayOfMonth()) {
            Duration onFirstDay = Duration.between(startDate, endDate);
            LogEntry entry = new LogEntry();
            entry.dateString = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase();
            entry.user = user;
            entry.duration = onFirstDay.toSeconds();
            entry.website = website;
            newEntries.add(entry);

        } else {
            int count = Period.between(startDate.toLocalDate(), endDate.toLocalDate()).getDays() + 1;

            for (int i = 0; i < count; i++) {
                LocalDateTime dayOfThePeriod = startDate.plusDays(i);
                if (dayOfThePeriod.getDayOfMonth() != endDate.getDayOfMonth()) {
                    Temporal dateToCompare = endDate;
                    LocalDateTime time = LocalDateTime.of(dayOfThePeriod.plusDays(1).toLocalDate(), LocalTime.of(0, 0));
                    if (time.isBefore(endDate)) {
                        dateToCompare = time;
                    }
                    Duration onCurrentDay = Duration.between(LocalDateTime.of(dayOfThePeriod.toLocalDate(), LocalTime.of(0, 0)), dateToCompare);
                    LogEntry entry = new LogEntry();
                    entry.dateString = dayOfThePeriod.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase();
                    entry.user = user;
                    entry.duration = onCurrentDay.toSeconds();
                    entry.website = website;
                    if (entry.duration > 0) {
                        newEntries.add(entry);
                    }
                } else {
                    Duration onCurrentDay = Duration.between(LocalDateTime.of(dayOfThePeriod.toLocalDate(), LocalTime.of(0, 0)), endDate);
                    LogEntry entry = new LogEntry();
                    entry.dateString = dayOfThePeriod.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase();
                    entry.user = user;
                    entry.duration = onCurrentDay.toSeconds();
                    entry.website = website;
                    if (entry.duration > 0) {
                        newEntries.add(entry);
                    }
                }

            }
        }

        return newEntries;
    }
}
