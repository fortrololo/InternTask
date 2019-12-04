package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LogMonitorTest {


    @Test
    void setA() {
        Path resourceDirectory = Paths.get("src","test","resources", "forMonitoring");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        Assertions.assertEquals("", absolutePath);
    }
}