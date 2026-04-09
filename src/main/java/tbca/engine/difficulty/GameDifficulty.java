package tbca.engine.difficulty;

import tbca.engine.logic.enemyai.AiType;

import java.util.List;

import static tbca.combatant.enemy.EnemyType.GOBLIN;
import static tbca.combatant.enemy.EnemyType.WOLF;
import static tbca.item.ItemType.POTION;

public enum GameDifficulty {
    EASY("Easy",
            "Suitable for players new to turn-based games!",
            List.of(
                // Wave 1
                new WaveBlueprint(List.of(
                        new EnemyBlueprint(GOBLIN),
                        new EnemyBlueprint(GOBLIN)
                ))
            )
    ),

    MEDIUM("Medium",
            "The default experience!",
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
            "For players who want to challenge themselves!",
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
            "WARNING: Enemies can now use items and skills. Select only if you're prepared...",
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
    private final String description;
    private final List<WaveBlueprint> enemySpawn;

    GameDifficulty(String label,
                   String description,
                   List<WaveBlueprint> enemySpawn) {
        this.label = label;
        this.description = description;
        this.enemySpawn = enemySpawn;
    }

    public List<WaveBlueprint> getEnemySpawnList() {
        return enemySpawn;
    }

    public int getTotalWaves() {
        return this.enemySpawn.size();
    }

    public int getTotalEnemies() {
        int result = 0;
        for (WaveBlueprint wave : this.enemySpawn) {
            result += wave.enemies().size();
        }
        return result;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return getLabel();
    }
}
