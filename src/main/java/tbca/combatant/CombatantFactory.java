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
import tbca.item.ItemType;
import tbca.item.Potion;
import tbca.item.PowerStone;
import tbca.item.SmokeBomb;

public final class CombatantFactory {

    private CombatantFactory() {}

    /**
     * @param playerClass
     * @param items
     * @return
     */
    public static Player createPlayer(PlayerClass playerClass, List<ItemType> itemTypes) {
        Player player = switch (playerClass) {
            case WARRIOR -> new Warrior();
            case WIZARD  -> new Wizard();
        };

        if (itemTypes != null) {
            for (ItemType type : itemTypes) {
                Item item = createItemFromType(type);
                player.addItem(item);
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