package tbca.ui.Battle;

import tbca.engine.GameStateReadOnly;

public class EndingScreen{
    public void showEndingScreen(GameStateReadOnly gameState) {
        displayEndingHeader();

        if (!gameState.isPlayerAlive()) {
            displayLossMessage();
        } else {
            displayVictoryMessage();
        }
    }

    private void displayEndingHeader() {
        System.out.println("\n=========================================");
        System.out.println("              GAME OVER                 ");
        System.out.println("=========================================\n");
    }

    private void displayLossMessage() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║           YOU LOST!                   ║");
        System.out.println("║    Better luck next time!             ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
    }

    private void displayVictoryMessage() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║           YOU WON!                    ║");
        System.out.println("║    Congratulations on your victory!   ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
    }
}