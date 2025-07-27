package dev.hdrelhaj;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import dev.hdrelhaj.studensystem.InvalidGradeException;
import dev.hdrelhaj.studensystem.StudentManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n📘 STUDENT GRADE MANAGER");
            System.out.println("1. Add Student");
            System.out.println("2. Add Grade");
            System.out.println("3. View All Averages");
            System.out.println("4. View Top N Students");
            System.out.println("5. Save to File");
            System.out.println("6. Load from File");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine().trim();
                    manager.addStudent(name);
                }
                case "2" -> {
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter grade (0-100): ");
                    try {
                        int grade = Integer.parseInt(scanner.nextLine().trim());
                        manager.addGrade(name, grade);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid number format.");
                    } catch (InvalidGradeException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                }
                case "3" -> manager.displayAllAverages();

                case "4" -> {
                    System.out.print("How many top students to view? ");
                    int n = Integer.parseInt(scanner.nextLine().trim());
//                    manager.displayTopNStudents(n);
                }
                case "5" -> {
                    System.out.print("Enter file name to save (e.g., students.txt): ");
                    String file = scanner.nextLine().trim();
                    manager.saveToFile(file);
                }
                case "6" -> {
                    System.out.print("Enter file name to load (e.g., students.txt): ");
                    String file = scanner.nextLine().trim();
                    manager.loadFromFile(file);
                }
                case "0" -> {
                    running = false;
                    System.out.println("👋 Exiting. Goodbye!");
                }
                default -> System.out.println("⚠️ Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
