package org.example.presenters;

import org.example.File;
import org.example.LogEntry;
import java.util.ArrayList;

public interface Presenter {
    void present(File file, ArrayList<LogEntry> entries);
}
