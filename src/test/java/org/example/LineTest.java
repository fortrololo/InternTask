package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void parse_lineContains2DaysLongOperation_2EntriesReturned() {
        Line line = new Line("1455839970,user1,http://ru.wikipedia.org,100");

        ArrayList<LogEntry> expectedOutput = new ArrayList<>();
        LogEntry entry1 = new LogEntry();
        entry1.user = "user1";
        entry1.dateString = "18-FEB-2016";
        entry1.website = "http://ru.wikipedia.org";
        entry1.duration = 30;

        LogEntry entry2 = new LogEntry();
        entry2.user = "user1";
        entry2.dateString = "19-FEB-2016";
        entry2.website = "http://ru.wikipedia.org";
        entry2.duration = 70;
        expectedOutput.add(entry1);
        expectedOutput.add(entry2);

        List<LogEntry> list = line.parse();

        Assertions.assertEquals(2, list.size());
        int numberOfMatches = 0;
        int expectedNumberOfMatches = 2;

        for (LogEntry entry : list) {
            for (LogEntry expectedOutputEntry : expectedOutput) {
                if (entry.duration == expectedOutputEntry.duration) {
                    numberOfMatches++;
                    Assertions.assertEquals(expectedOutputEntry.user, entry.user);
                    Assertions.assertEquals(expectedOutputEntry.dateString, entry.dateString);
                    Assertions.assertEquals(expectedOutputEntry.website, entry.website);
                }
            }
        }

        Assertions.assertEquals(expectedNumberOfMatches, numberOfMatches);
    }

    @Test
    void parse_lineContains1DayLongOperation_1EntryReturned() {
        Line line = new Line("1455839900,user1,http://ru.wikipedia.org,100");

        ArrayList<LogEntry> expectedOutput = new ArrayList<>();
        LogEntry entry = new LogEntry();
        entry.user = "user1";
        entry.dateString = "18-FEB-2016";
        entry.website = "http://ru.wikipedia.org";
        entry.duration = 100;

        List<LogEntry> list = line.parse();

        Assertions.assertEquals(1, list.size());
        LogEntry expectedOutputEntry = list.get(0);
        Assertions.assertEquals(expectedOutputEntry.user, entry.user);
        Assertions.assertEquals(expectedOutputEntry.dateString, entry.dateString);
        Assertions.assertEquals(expectedOutputEntry.website, entry.website);
    }

    @Test
    void parse_lineContains3DaysLongOperation_3EntriesReturned() {
        Line line = new Line("1455839970,user1,http://ru.wikipedia.org,86490");

        ArrayList<LogEntry> expectedOutput = new ArrayList<>();
        LogEntry entry1 = new LogEntry();
        entry1.user = "user1";
        entry1.dateString = "18-FEB-2016";
        entry1.website = "http://ru.wikipedia.org";
        entry1.duration = 30;

        LogEntry entry2 = new LogEntry();
        entry2.user = "user1";
        entry2.dateString = "19-FEB-2016";
        entry2.website = "http://ru.wikipedia.org";
        entry2.duration = 86400;

        LogEntry entry3 = new LogEntry();
        entry3.user = "user1";
        entry3.dateString = "20-FEB-2016";
        entry3.website = "http://ru.wikipedia.org";
        entry3.duration = 60;

        expectedOutput.add(entry1);
        expectedOutput.add(entry2);
        expectedOutput.add(entry3);

        List<LogEntry> list = line.parse();

        Assertions.assertEquals(3, list.size());
        int numberOfMatches = 0;
        int expectedNumberOfMatches = 3;

        for (LogEntry entry : list) {
            for (LogEntry expectedOutputEntry : expectedOutput) {
                if (entry.duration == expectedOutputEntry.duration) {
                    numberOfMatches++;
                    Assertions.assertEquals(expectedOutputEntry.user, entry.user);
                    Assertions.assertEquals(expectedOutputEntry.dateString, entry.dateString);
                    Assertions.assertEquals(expectedOutputEntry.website, entry.website);
                }
            }
        }

        Assertions.assertEquals(expectedNumberOfMatches, numberOfMatches);
    }
}