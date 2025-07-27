package dev.hdrelhaj.studensystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentManager {
    private Map<String, List<Integer>> studentGrades = new HashMap<>();

    public void addStudent(String name) {
        studentGrades.putIfAbsent(name, List.of(0,0,0));
    }

    public void addGrade(String name, int grade) throws InvalidGradeException {
        if (grade < 0 || grade > 100) {
            throw new InvalidGradeException("Grade must be between 0 and 100");
        }
        studentGrades.computeIfAbsent(name, k -> new ArrayList<>()).add(grade);
    }

    public double getAverage(String name) {
        return studentGrades.getOrDefault(name, List.of())
                .stream()
                .mapToInt(i -> i)
                .average()
                .orElse(0);
    }

    public void displayAllAverages() {
        studentGrades.forEach(
                ((name, grade) -> {
                    double avg = grade.stream().mapToInt(i -> i).average().orElse(0);
                    System.out.printf("%s: %.2f%n", name, avg);
                })
        );
    }

    public void saveToFile(String fileName) {

        List<String> lines = studentGrades.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ":" +
                        entry.getValue().stream()
                                .map(String::valueOf)
                                .collect(Collectors.joining(",")))
                .collect(Collectors.toList());

        try {
            Path filePath = Paths.get(fileName);
            Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("✅ Data saved to " + fileName);
        } catch (IOException e) {
            System.err.println("❌ Failed to save to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String fileName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line: lines) {
                String[] parts = line.split(":");
                if (parts.length != 2)
                    continue;
                String name = parts[0].trim();
                String[] grades = parts[1].split(",");
                List<Integer> gradeList = new ArrayList<>();
                for (String grade: grades) {
                    try {
                        gradeList.add(Integer.parseInt(grade.trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("❌ Invalid grade: " + grade);
                    }
                }
                studentGrades.put(name, gradeList);
            }
            System.out.println("✅ Datd loaded from " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Fialed to load data from " + fileName);

        }

    }


}
