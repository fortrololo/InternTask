package org.example;

public class App {

    public static void main(String[] args) {
        LogMonitor monitor = new LogMonitor("/home/ilnar/IdeaProjects/InterTask/src/main/resources/logs/1.csv");
        monitor.run();

//        App app = new App();
//        app.run();
    }

}
