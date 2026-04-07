package tbca.engine;

import tbca.combatant.Combatant;
import tbca.combatant.CombatantFactory;
import tbca.engine.difficulty.EnemyBlueprint;
import tbca.engine.difficulty.GameDifficulty;
import tbca.engine.difficulty.WaveBlueprint;

import java.util.ArrayList;
import java.util.List;

public class GameState implements GameStateReadOnly {
    private final GameDifficulty difficulty;
    private Combatant player;
    private List<Combatant> currEnemies = new ArrayList<>();;

    private int currWave = 0; // 1-indexed, 0 signifies no wave started
    private int currTurn = 0;

    public GameState(Combatant player, GameDifficulty difficulty) {
        this.player = player;
        this.difficulty = difficulty;
    }

    public void spawnNextWave() {
        if (!this.hasMoreWaves()) {
            return;
        }
        WaveBlueprint nextWaveSpawn = difficulty.getEnemySpawnList().get(currWave);
        currWave++;

        this.currEnemies.clear();
        for (EnemyBlueprint enemy : nextWaveSpawn.enemies()) {
            this.currEnemies.add(CombatantFactory.createEnemy(enemy.enemyType(), enemy.startingItems()));
        }
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

    public void incrementTurn() {
        currTurn++;
    }

    @Override
    public int currWave() {
        return currWave;
    }

    @Override
    public int getCurrTurn() {
        return currTurn;
    }

    @Override
    public int getTotalWaves() {
        return difficulty.getEnemySpawnList().size();
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
                (currWave == difficulty.getEnemySpawnList().size() && this.allCurrWaveEnemiesDead());
    }
}
