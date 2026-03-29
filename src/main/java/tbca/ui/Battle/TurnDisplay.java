package tbca.ui.Battle;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;
import java.util.List;

public class TurnDisplay {
    public void displayTurnEnd(GameStateReadOnly gameState) {
        displayPlayerAndEnemyStats(gameState);
        displayItemsAndCooldown(gameState);
    }

    public void displayTurnStart(GameStateReadOnly gameState) {
        System.out.printf("=== Wave %d ===%n", gameState.currWave());
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
            System.out.printf("Enemy %c HP: %d",
                    (char)('A' + i),
                    enemy.getCurrHp()
            );
            if (i < enemies.size() - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    private void displayItemsAndCooldown(GameStateReadOnly gameState) {
        System.out.printf("Items/Cooldowns: N/A (Wave %d)%n%n", gameState.currWave());
    }


}