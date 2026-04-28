package ui;

import models.User;
import services.AuthService;
import interfaces.CanViewSystem;
import java.util.Scanner;

public class ConsoleEngine {
    private final AuthService authService;
    private final Scanner scanner;
    private boolean isRunning;

    public ConsoleEngine() {
        this.authService = new AuthService();
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
    }

    public void run() {
        System.out.println("Information System of Research-Oriented University");

        while (isRunning) {
            if (!authService.isLoggedIn()) {
                showLoginMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n--- Login ---");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authService.login(name, password)) {
            System.out.println("Success");
        } else {
            System.out.println("Invalid data");
        }
    }

    private void showMainMenu() {
        User currentUser = authService.getCurrentUser();
        
        if (currentUser instanceof CanViewSystem) {
            ((CanViewSystem) currentUser).ViewMenu();
        }

        String choice = scanner.nextLine();
        handleChoice(choice);
    }

    private void handleChoice(String choice) {
        if (choice.equals("0")) {
            authService.logout();
        } else {
            System.out.println("Processing option " + choice + "...");
            
        }
    }
}