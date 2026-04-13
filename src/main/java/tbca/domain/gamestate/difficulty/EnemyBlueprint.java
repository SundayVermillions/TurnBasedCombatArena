package tbca.domain.gamestate.difficulty;

import tbca.domain.combatant.enemy.EnemyType;
import tbca.engine.logic.enemyai.AiType;
import tbca.domain.item.ItemType;

import java.util.List;

public record EnemyBlueprint(EnemyType enemyType, AiType ai, List<ItemType> startingItems) {
    // overloaded constructor for easy, normal, and hard modes where there are no items and simple ai
    public EnemyBlueprint(EnemyType enemyType) {
        this(enemyType, AiType.SIMPLE, List.of());
    }

    // overloaded constructor for when enemy spawns with no items
    public EnemyBlueprint(EnemyType enemyType, AiType ai) {
        this(enemyType, ai, List.of());
    }
}
