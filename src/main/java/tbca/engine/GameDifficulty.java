package tbca.engine;

import java.util.List;
import java.util.Map;

import tbca.combatant.enemy.EnemyType;
import static tbca.combatant.enemy.EnemyType.GOBLIN;
import static tbca.combatant.enemy.EnemyType.WOLF;

public enum GameDifficulty {
    EASY("Easy",
            List.of(
                // Wave 1
                Map.of(GOBLIN, 2)
            )
    ),

    MEDIUM("Medium",
            List.of(
                    // Wave 1
                    Map.of(GOBLIN, 1, WOLF, 1),
                    // Wave 2
                    Map.of(WOLF, 2)
            )
    ),

    HARD("Hard",
            List.of(
                    // Wave 1
                    Map.of(GOBLIN, 2),
                    // Wave 2
                    Map.of(GOBLIN,1, WOLF, 2)
            )
    );


    private final String label;
    private final List<Map<EnemyType, Integer>> enemySpawn;

    GameDifficulty(String label,
                   List<Map<EnemyType, Integer>> enemySpawn) {
        this.label = label;
        this.enemySpawn = enemySpawn;
    }

    public List<Map<EnemyType, Integer>> getEnemySpawnList() {
        return enemySpawn;
    }


    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}
