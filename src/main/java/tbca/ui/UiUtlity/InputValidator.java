package tbca.ui.UiUtlity;
import java.util.Scanner;

public class InputValidator {
    private Scanner scanner = new Scanner(System.in);

    public InputValidator(Scanner scanner) {
        this.scanner = scanner;
    }
    public int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}
