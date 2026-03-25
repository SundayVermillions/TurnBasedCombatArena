package tbca.ui.Battle;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;
import tbca.combatant.enemy.Enemy;
import tbca.item.ItemType;

import java.io.PrintStream;
import java.util.List;

public class TurnDisplay {
    public void displayTurnEnd(GameStateReadOnly gameState) {
        displayPlayerAndEnemyStats(gameState);
        displayItemsAndCooldown(gameState);
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
            System.out.printf("Goblin %c HP: %d",
                    (char)('A' + i),
                    enemy.getCurrHp()
            );
            /*
            if(enemy.isStun())
            {
                System.out.print("[Stunned]");
            }
            */
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
}