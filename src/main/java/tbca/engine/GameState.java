package tbca.engine;

import tbca.combatant.Combatant;
import tbca.combatant.CombatantFactory;
import tbca.combatant.enemy.EnemyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameState implements GameStateReadOnly {
    private final GameDifficulty difficulty;
    private Combatant player;
    private List<Combatant> currEnemies = new ArrayList<>();;

    private int currWave = 0; // 1-indexed, 0 signifies no wave started

    public GameState(Combatant player, GameDifficulty difficulty) {
        this.player = player;
        this.difficulty = difficulty;
    }

    public void spawnNextWave() {
        if (!this.hasMoreWaves()) {
            return;
        }
        Map<EnemyType, Integer> nextWaveSpawn = difficulty.getEnemySpawnList().get(currWave);
        currWave++;

        this.currEnemies.clear();
        nextWaveSpawn.forEach((enemyType, count) -> {
            for (int i = 0; i < count; i++) {
                this.currEnemies.add(CombatantFactory.createEnemy(enemyType));
            }
        });
    }

    public boolean isPlayerAlive() {
        return !this.player.isDead();
    }

    public boolean allCurrWaveEnemiesDead() {
        if (currEnemies.isEmpty())
            return true;

        for (Combatant enemy : currEnemies)
            if (!enemy.isDead())
                return false;

        return true;
    }

    @Override
    public int currWave() {
        return currWave;
    }

    @Override
    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public Combatant getPlayer() {
        return player;
    }

    @Override
    public List<Combatant> getCurrEnemies() {
        return currEnemies;
    }

    @Override
    public boolean hasMoreWaves() {
        return currWave < difficulty.getEnemySpawnList().size();
    }

    @Override
    public boolean hasGameEnded() {
        return !isPlayerAlive() ||
                currWave > difficulty.getEnemySpawnList().size();
    }
}
