package ua.lpnu.battle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BattleLogger {
    private final List<String> logHistory = new ArrayList<>();

    public void log(String message) {
        System.out.println(message);
        logHistory.add(message);
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(filename)))) {
            logHistory.forEach(writer::println);
            System.out.printf("Battle successfully recorded to file: %s\n", filename);
        } catch (IOException e) {
            System.err.printf("Error when saving the battle to a file: %s\n ", e.getMessage());
        }
    }
}
