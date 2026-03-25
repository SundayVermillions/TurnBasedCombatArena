package tbca.ui.Menu;

import tbca.engine.GameDifficulty;
import tbca.ui.Input.InputValidator;

import java.util.Scanner;

public class Difficulty {
    InputValidator inputValidator;
    public Difficulty() {
        this.inputValidator = new InputValidator(new Scanner(System.in));
    }

    public GameDifficulty promptDifficulty() {
        displayDifficultyOptions();
        int choice = inputValidator.getIntInput("Enter choice: ", 1, 3);
        return switch (choice) {
            case 1 -> GameDifficulty.EASY;
            case 2 -> GameDifficulty.MEDIUM;
            case 3 -> GameDifficulty.HARD;
            default -> {
                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                yield GameDifficulty.MEDIUM;
            }
        };
    }

    private void displayDifficultyOptions() {
        System.out.println("\nSelect game difficulty:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
    }
}