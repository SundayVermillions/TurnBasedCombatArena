package tbca.combatant;

import java.util.List;

import tbca.combatant.enemy.EnemyType;
import tbca.combatant.enemy.Goblin;
import tbca.combatant.enemy.Wolf;
import tbca.combatant.player.Player;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.combatant.player.playerclass.Warrior;
import tbca.combatant.player.playerclass.Wizard;
import tbca.item.Item;

public final class CombatantFactory {

    private CombatantFactory() {}

    /**
     * @param playerClass
     * @param items
     * @return
     */
    public static Player createPlayer(PlayerClass playerClass, List<Item> items) {
        Player player = switch (playerClass) {
            case WARRIOR -> new Warrior();
            case WIZARD  -> new Wizard();
        };
        for (Item item : items) {
            player.addItem(item);
        }
        return player;
    }

    /**
     * @param enemyType
     * @return
     */
    public static Combatant createEnemy(EnemyType enemyType) {
        return switch (enemyType) {
            case GOBLIN -> new Goblin();
            case WOLF   -> new Wolf();
        };
    }
}