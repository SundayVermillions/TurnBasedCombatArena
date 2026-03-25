package tbca.ui;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.GameDifficulty;
import tbca.engine.action.ActionParameters;
import tbca.item.Item;
import java.util.Scanner;
import java.util.List;

public class TESTING {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI(); // Interface reference
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== UI Testing Menu ===");
            System.out.println("1. Display Menu");
            System.out.println("2. Prompt Difficulty");
            System.out.println("3. Prompt Class Selection");
            System.out.println("4. Prompt Item Selection");
            System.out.println("5. Display Turn Start");
            System.out.println("6. Get Player Action");
            System.out.println("7. Show Ending Screen");
            System.out.println("8. Display Turn End");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    ui.displayMenu();
                    break;
                case "2":
                    GameDifficulty difficulty = ui.promptDifficulty();
                    System.out.println("Selected difficulty: " + difficulty);
                    break;
                case "3":
                    PlayerClass playerClass = ui.promptClassSelection();
                    System.out.println("Selected class: " + playerClass);
                    break;
                case "4":
                    List<Item> items = ui.promptItemSelection();
                    System.out.println("Selected items: " + items);
                    break;
                case "5":
                    // For testing, pass null or a dummy GameStateReadOnly
                    ui.displayTurnStart(null);
                    break;
                case "6":
                    ActionParameters action = ui.getPlayerAction(null);
                    System.out.println("Player action: " + action);
                    break;
                case "7":
                    ui.showEndingScreen(null);
                    break;
                case "8":
                    ui.displayTurnEnd(null);
                    break;
                case "9":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        System.out.println("UI Testing ended.");
        scanner.close();
    }
}
