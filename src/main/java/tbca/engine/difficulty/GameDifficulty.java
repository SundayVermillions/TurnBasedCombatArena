package tbca.engine.difficulty;

import tbca.engine.logic.enemyai.AiType;

import java.util.List;

import static tbca.combatant.enemy.EnemyType.GOBLIN;
import static tbca.combatant.enemy.EnemyType.WOLF;
import static tbca.item.ItemType.POTION;

public enum GameDifficulty {
    EASY("Easy",
            List.of(
                // Wave 1
                new WaveBlueprint(List.of(
                        new EnemyBlueprint(GOBLIN),
                        new EnemyBlueprint(GOBLIN)
                ))
            )
    ),

    MEDIUM("Medium",
            List.of(
                    // Wave 1
                    new WaveBlueprint(List.of(
                            new EnemyBlueprint(GOBLIN),
                            new EnemyBlueprint(WOLF)
                    )),
                    // Wave 2
                    new WaveBlueprint(List.of(
                            new EnemyBlueprint(WOLF),
                            new EnemyBlueprint(WOLF)
                    ))
            )
    ),

    HARD("Hard",
            List.of(
                    // Wave 1
                    new WaveBlueprint(List.of(
                            new EnemyBlueprint(GOBLIN),
                            new EnemyBlueprint(GOBLIN)
                    )),
                    // Wave 2
                    new WaveBlueprint(List.of(
                            new EnemyBlueprint(GOBLIN),
                            new EnemyBlueprint(WOLF),
                            new EnemyBlueprint(WOLF)
                    ))
            )
    ),

    EXTREME("Extreme",
            List.of(
                    // Wave 1
                    new WaveBlueprint(List.of(
                            new EnemyBlueprint(GOBLIN, AiType.ADVANCED, List.of(POTION)),
                            new EnemyBlueprint(GOBLIN, AiType.ADVANCED, List.of(POTION))
                    )),
                    // Wave 2
                    new WaveBlueprint(List.of(
                            new EnemyBlueprint(GOBLIN, AiType.ADVANCED, List.of(POTION)),
                            new EnemyBlueprint(WOLF, AiType.ADVANCED),
                            new EnemyBlueprint(WOLF, AiType.ADVANCED)
                    ))
            ));


    private final String label;
    private final List<WaveBlueprint> enemySpawn;

    GameDifficulty(String label,
                   List<WaveBlueprint> enemySpawn) {
        this.label = label;
        this.enemySpawn = enemySpawn;
    }

    public List<WaveBlueprint> getEnemySpawnList() {
        return enemySpawn;
    }


    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
