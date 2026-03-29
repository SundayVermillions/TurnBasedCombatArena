package tbca.ui.BattleDisplay;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;
import java.util.List;

public class DisplayOnly {
    public void displayMenu() {
        System.out.println("=========================================");
        System.out.println("       TURN-BASED COMBAT ARENA           ");
        System.out.println("=========================================\n");
    }
    public void displayTurnEnd(GameStateReadOnly gameState) {
        displayPlayerAndEnemyStats(gameState);
        displayItemsAndCooldown(gameState);
    }

    public void displayTurnStart(GameStateReadOnly gameState) {
        System.out.printf("=== Round %d ===\n", gameState.currWave());
    }

    private void displayPlayerAndEnemyStats(GameStateReadOnly gameState) {
        // Player HP
        System.out.printf("%s HP: %d/%d | ",
                gameState.getPlayer().getClass().getSimpleName(),
                gameState.getPlayer().getCurrHp(),
                gameState.getPlayer().getMaxHp()
        );

        // Enemies HP
        List<Combatant> enemies = gameState.getCurrEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            Combatant enemy = enemies.get(i);
            System.out.printf("%s %c HP: %d",
                    enemy.getName(),
                    (char)('A' + i),
                    enemy.getCurrHp()
            );

            if(!enemy.canAct())
            {
                System.out.print("[Stunned]");
            }

            if (i < enemies.size() - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    private void displayItemsAndCooldown(GameStateReadOnly gameState) {
        /*
        PrintStream printf = System.out.printf("Potion: %d | Smoke Bomb: %d | Special Skills Cooldown: %d Round",
                gameState.getPlayer().getItemCount(ItemType.POTION),
                gameState.getPlayer().getItemCount(ItemType.SMOKE_BOMB),
                gameState.getPlayer()
        );
         */
        System.out.println();
    }


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