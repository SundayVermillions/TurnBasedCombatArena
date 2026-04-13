package tbca.domain.combatant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tbca.domain.combatant.enemy.EnemyType;
import tbca.domain.combatant.enemy.Goblin;
import tbca.domain.combatant.enemy.Wolf;
import tbca.domain.combatant.player.Player;
import tbca.domain.combatant.player.playerclass.PlayerClass;
import tbca.domain.combatant.player.playerclass.Warrior;
import tbca.domain.combatant.player.playerclass.Wizard;
import tbca.domain.item.Item;
import tbca.domain.item.ItemType;
import tbca.domain.item.Potion;
import tbca.domain.item.PowerStone;
import tbca.domain.item.SmokeBomb;
import tbca.engine.logic.enemyai.AiType;

public final class CombatantFactory {
    private static final Map<EnemyType, Integer> enemyCounts = new HashMap<>();

    private CombatantFactory() {}

    public static Player createPlayer(PlayerClass playerClass, List<ItemType> itemTypes) {
        Player player = switch (playerClass) {
            case WARRIOR -> new Warrior();
            case WIZARD  -> new Wizard();
        };

        if (itemTypes != null && !itemTypes.isEmpty()) {
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

    // TODO: createEnemy(EnemyType enemyType, AiType aiType)
    // TODO: also, try not to use null, an empty list is better cause others can use built-in empty() to check
    public static Combatant createEnemy(EnemyType enemyType, AiType aiType) {
        return createEnemy(enemyType, aiType, java.util.Collections.emptyList());
    }

    // TODO: createEnemy(EnemyType enemyType, AiType aiType, List<ItemType> itemTypes)
    // TODO: also need an Enemy method getAiType to return the AiType enum
    public static Combatant createEnemy(EnemyType enemyType, AiType aiType, List<ItemType> itemTypes) {
        int count = enemyCounts.getOrDefault(enemyType, 0);
        String suffix = String.valueOf((char) ('A' + count));
        enemyCounts.put(enemyType, count + 1);

        Combatant enemy = switch (enemyType) {
            case GOBLIN -> new Goblin(suffix);
            case WOLF   -> new Wolf(suffix);
        };

        enemy.setAiType(aiType);

        if (itemTypes != null) {
            for (ItemType type : itemTypes) {
                if (type == ItemType.POTION) {
                    enemy.addItem(createItemFromType(type));
                } else {
                    throw new IllegalArgumentException("Enemy item error: " + type + "is nots supported for enemies.");
                }
            }
        }

        return enemy;
    }

    public static void resetCounters() {
        enemyCounts.clear();
    }
}