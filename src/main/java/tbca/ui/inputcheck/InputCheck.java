package tbca.ui.inputcheck;

import java.util.InputMismatchException;
import java.util.Scanner;

/* this is a subpackage for user input checking*/
public class InputCheck {
    private Scanner scanner;

    public InputCheck() {
        this.scanner = new Scanner(System.in);
    }

    // Get the integer from user within the choices range
    public int getInt(int min, int max, String message) {
        int choice = -1;
        boolean valid = false;
        while (!valid) {
            System.out.print(message);
            try {
                choice = scanner.nextInt();
                if (choice >= min && choice <= max) {
                    valid = true;
                } else {
                    System.out.println("Invalid input. Please enter a valid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return choice;
    }
}
