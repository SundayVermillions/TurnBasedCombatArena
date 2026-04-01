package tbca.combatant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tbca.combatant.enemy.EnemyType;
import tbca.combatant.enemy.Goblin;
import tbca.combatant.enemy.Wolf;
import tbca.combatant.player.Player;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.combatant.player.playerclass.Warrior;
import tbca.combatant.player.playerclass.Wizard;
import tbca.item.Item;
import tbca.item.ItemType;
import tbca.item.Potion;
import tbca.item.PowerStone;
import tbca.item.SmokeBomb;

public final class CombatantFactory {
    private static final Map<EnemyType, Integer> enemyCounts = new HashMap<>();

    private CombatantFactory() {}

    public static Player createPlayer(PlayerClass playerClass, List<ItemType> itemTypes) {
        Player player = switch (playerClass) {
            case WARRIOR -> new Warrior();
            case WIZARD  -> new Wizard();
        };

        if (itemTypes != null) {
            for (ItemType type : itemTypes) {
                player.addItem(createItemFromType(type));
            }
        }
        return player;
    }

    private static Item createItemFromType(ItemType type) {
        return switch (type) {
            case POTION -> new Potion();   
            case POWER_STONE -> new PowerStone(); 
            case SMOKE_BOMB -> new SmokeBomb();   
        };
    }
    public static Combatant createEnemy(EnemyType enemyType) {
        int count = enemyCounts.getOrDefault(enemyType, 0);
        String suffix = String.valueOf((char) ('A' + count));
        
        enemyCounts.put(enemyType, count + 1);

        return switch (enemyType) {
            case GOBLIN -> new Goblin(suffix);
            case WOLF   -> new Wolf(suffix);
        };
    }
    public static void resetCounters() {
        enemyCounts.clear();
    }
}