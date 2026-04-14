package tbca.ui.EndingScreen;
import tbca.domain.gamestate.GameStateReadOnly;
import tbca.domain.item.Item;
import tbca.ui.BattleScreen.BattleScreen;
import tbca.ui.UiUtlity.InputValidator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EndingScreen {
    InputValidator inputValidator;
    BattleScreen gameplayScreen;

    public EndingScreen()
    {
        gameplayScreen = new BattleScreen();
        inputValidator = new InputValidator(new Scanner(System.in));
    }

    public void showEndingScreen(GameStateReadOnly gameState) {
        gameplayScreen.displayTurnStart(gameState);
        System.out.println();

        displayFinalSummaryHeader();

        System.out.println(buildOutcomeSentence(gameState));
        System.out.println();

        if (!gameState.isPlayerAlive()) {
            displayLossMessage();
        } else {
            displayVictoryMessage();
        }
    }

    private void displayFinalSummaryHeader() {
        System.out.println("\n=========================================");
        System.out.println("          Final Game State Summary       ");
        System.out.println("=========================================\n");
    }

    private void displayLossMessage() {
        System.out.println("=========================================");
        System.out.println("              YOU LOST!                  ");
        System.out.println("         Better luck next time!          ");
        System.out.println("=========================================\n");
    }

    private void displayVictoryMessage() {
        System.out.println("=========================================");
        System.out.println("              YOU WON!                   ");
        System.out.println("       Congratulations on your victory!  ");
        System.out.println("=========================================\n");
    }

    private String buildOutcomeSentence(GameStateReadOnly gameState) {
        String progress = "Wave " + gameState.currWave() + " Turn " + gameState.getCurrTurn();
        String itemsLeft = formatItemsLeft(gameState.getPlayer().getInventory());

        if (gameState.isPlayerAlive()) {
            return "Good job! You survived until " + progress + " with "
                    + gameState.getPlayer().getCurrHp() + " HP remaining. \nItems left: " + itemsLeft + ".";
        }

        return "Oh no! You reached " + progress + ", but "
                + gameState.getNumOfRemainingEnemies() + " enemies are still remaining. \nItems left: "
                + itemsLeft + ".";
    }

    private String formatItemsLeft(List<Item> inventory) {
        if (inventory.isEmpty()) {
            return "none";
        }

        Map<String, Integer> groupedItems = new LinkedHashMap<>();
        for (Item item : inventory) {
            String itemName = item.getName();
            groupedItems.put(itemName, groupedItems.getOrDefault(itemName, 0) + 1);
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : groupedItems.entrySet()) {
            if (!result.isEmpty()) {
                result.append(", ");
            }
            result.append(entry.getKey()).append(" x").append(entry.getValue());
        }
        return result.toString();
    }

    public EndingScreenOptions promptEndingScreenChoice() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Replay with Same Settings");
        System.out.println("2. Start New Game");
        System.out.println("3. Exit Game");
        System.out.println();

        int choice = inputValidator.getIntInput("Enter choice: ", 1, 3);
        return switch (choice) {
            case 1 -> EndingScreenOptions.REPLAY_SAME_SETTINGS;
            case 2 -> EndingScreenOptions.START_NEW;
            case 3 -> EndingScreenOptions.EXIT;
            default -> null;
        };
    }


}
