package services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    private final List<String> logs = new ArrayList<>();
    private final String filename;

    public Logger(String filename) {
        this.filename = filename;
        writeHeader();
    }

    public void addLog(String message) {
        logs.add(message);
        writeToFile(message);
    }

    public void writeHeader() {
        try (FileWriter fw = new FileWriter(filename, false)) {
            fw.write("--------- Event Log ----------\n");
        } catch (IOException e) {
            System.out.println("Unable to write to the logfile.");
        }
    }

    private void writeToFile(String message) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            System.out.println("Unable to write to the logfile.");
        }
    }

    public void printLog() {
        System.out.println("\n--------- Mission Log --------");

        for (String log : logs) {
            System.out.println(log);
        }
    }
}
