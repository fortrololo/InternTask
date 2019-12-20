package org.example;

import java.time.LocalDate;

public class LogEntry {
    public String user;
    public String website;
    public long duration;
    public String dateString;

    @Override
    public String toString() {
        return "LogEntry{" +
                "user='" + user + '\'' +
                ", website='" + website + '\'' +
                ", duration=" + duration +
                ", dateString='" + dateString + '\'' +
                '}';
    }
}
