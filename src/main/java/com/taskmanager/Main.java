package com.taskmanager;

import com.taskmanager.cli.CommandHandler;

import java.util.Scanner;

/**
 * Main entry point for the Task Manager application.
 * Demonstrates Java 25 simplified main method and modern CLI application structure.
 * 
 * @author Developer
 * @version 1.0.0
 */
public class Main {
    private static final String VERSION = "1.0.0";
    private static final String BANNER = """
            
            ╔════════════════════════════════════════════════════════════════════════════╗
            ║                                                                            ║
            ║                          TASK MANAGER v%s                              ║
            ║                                                                            ║
            ║                   A Modern Java 25 LTS Application                         ║
            ║                                                                            ║
            ╚════════════════════════════════════════════════════════════════════════════╝
            
            Welcome! Type 'help' to see available commands or 'exit' to quit.
            
            """;

    public static void main(String[] args) {
        // Display welcome banner
        System.out.printf(BANNER, VERSION);
        
        // Initialize command handler
        CommandHandler commandHandler = new CommandHandler();
        
        // Main application loop
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("task-manager> ");
                String input = scanner.nextLine().trim();
                
                if (input.isEmpty()) {
                    continue;
                }
                
                commandHandler.processCommand(input);
            }
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}

