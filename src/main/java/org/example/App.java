package org.example;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class App {

    public static void main(String[] args) {

        String pathString = "/home/ilnar/IdeaProjects/InterTask/src/main/resources/logs/1.csv";
        File file = new File(Paths.get(pathString));
        Map<String, ArrayList<LogEntry>> res = file.parse();
        res.forEach(
                (k, v) -> {
                    System.out.println(k);
                    System.out.println(v);
                }
        );
//        LogMonitor monitor = new LogMonitor("/home/ilnar/IdeaProjects/InterTask/src/main/resources/logs/1.csv");
//        monitor.run();



//        App app = new App();
//        app.run();
    }

}
