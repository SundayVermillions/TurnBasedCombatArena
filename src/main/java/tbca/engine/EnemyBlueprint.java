package tbca.engine;

import tbca.combatant.enemy.EnemyType;
import tbca.item.ItemType;

import java.util.List;

public record EnemyBlueprint(EnemyType enemyType, List<ItemType> startingItems) {
    // overloaded constructor for easy, normal, and hard modes where there are no items
    public EnemyBlueprint(EnemyType enemyType) {
        this(enemyType, List.of());
    }
}
