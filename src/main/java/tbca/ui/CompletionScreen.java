package tbca.ui;

import tbca.engine.GameStateReadOnly;

import java.util.Scanner;

public class CompletionScreen {
    InputValidator inputValidator;
    GameplayScreen gameplayScreen;

    public CompletionScreen()
    {
        gameplayScreen = new GameplayScreen();
        inputValidator = new InputValidator(new Scanner(System.in));
    }

    public void showEndingScreen(GameStateReadOnly gameState) {
        displayEndingHeader();

        if (!gameState.isPlayerAlive()) {
            displayLossMessage();
        } else {
            displayVictoryMessage();
        }

        System.out.println("--- Final Game State Summary ---");
        gameplayScreen.displayTurnStart(gameState);
        System.out.println();
    }

    private void displayEndingHeader() {
        System.out.println("\n=========================================");
        System.out.println("              GAME OVER                 ");
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
