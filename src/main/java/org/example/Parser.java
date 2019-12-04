package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Parser {

//    public LogEntry parseLine(String line) {
//        String[] parts = line.split(",");
//        int unixTimestamp = Integer.parseInt(parts[0]);
//        String user = parts[1];
//        String website = parts[2];
//        long duration = Integer.parseInt(parts[3]);
//
//        Date date1 = new Date((long) unixTimestamp * 1000);
//        LocalDateTime startDate = date1.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
//        Date date2 = new Date((long) (unixTimestamp + duration) * 1000);
//        LocalDateTime endDate = date2.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
//        if (startDate.getDayOfMonth() == endDate.getDayOfMonth()) {
//            Duration onFirstDay = Duration.between(LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(23, 59)), startDate);
//            LogEntry entry = new LogEntry();
//            entry.date = startDate.toLocalDate();
//            entry.user = user;
//            entry.duration = onFirstDay.toSeconds();
//            entry.website = website;
//
//
//        } else {
//            Duration onFirstDay = Duration.between(LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(23, 59)), startDate);
//            String result = "" + startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase()
//                    + "\n"
//                    + ""
//                    + unixTimestamp
//                    + ","
//                    + user
//                    + ","
//                    + website
//                    + ","
//                    + onFirstDay.toSeconds();
//            Duration onSecondDay = Duration.between(LocalDateTime.of(endDate.toLocalDate(), LocalTime.of(0, 0)), endDate);
//            result += "\n" + endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")).toUpperCase()
//                    + "\n"
//                    + ""
//                    + unixTimestamp
//                    + ","
//                    + user
//                    + ","
//                    + website
//                    + ","
//                    + onSecondDay.toSeconds();
//
//            return result;
//        }
//    }

}
