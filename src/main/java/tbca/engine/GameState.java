package tbca.engine;

import tbca.combatant.Combatant;
import tbca.combatant.CombatantFactory;
import tbca.effect.FieldEffect;
import tbca.engine.difficulty.EnemyBlueprint;
import tbca.engine.difficulty.GameDifficulty;
import tbca.engine.difficulty.WaveBlueprint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState implements GameStateReadOnly {
    private final GameDifficulty difficulty;
    private Combatant player;
    private List<Combatant> currEnemies = new ArrayList<>();
    private List<FieldEffect> fieldEffects = new ArrayList<>();

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
            this.currEnemies.add(CombatantFactory.createEnemy(enemy.enemyType(), enemy.ai(), enemy.startingItems()));
        }
    }
    public void addFieldEffect(FieldEffect effect) {

        this.fieldEffects.add(effect);


        effect.applyEffect(this);
    }

    public boolean isPlayerAlive() {
        return !this.player.isDead();
    }

    public boolean allCurrWaveEnemiesDead() {
        if (currEnemies.isEmpty())
            return true;

        for (Combatant enemy : currEnemies)
            if (enemy.isAlive())
                return false;

        return true;
    }

    public void incrementTurn() {
        currTurn++;
    }

    public void tickAllFieldEffects() {
        for (FieldEffect fieldEffect : this.getActiveFieldEffects()) {
            fieldEffect.tick();
            if (fieldEffect.getTurnsRemaining() == 0) {
                fieldEffect.removeEffect(getAllCombatants());
                this.fieldEffects.remove(fieldEffect);
            }
        }
    }

    public List<Combatant> getAllCombatants() {
        ArrayList<Combatant> combatants = new ArrayList<>();
        combatants.add(getPlayer());
        combatants.addAll(getCurrEnemies());
        return combatants;
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
        return difficulty.getTotalWaves();
    }

    @Override
    public int getNumOfRemainingEnemies() {
        int total = 0;
        for (Combatant c : getCurrEnemies()) {
            if (c.isAlive())
                total++;
        }
        for (int wave = currWave; wave < getTotalWaves(); wave++) {
            total += difficulty.getEnemySpawnList().get(wave).enemies().size();
        }
        return total;
    }

    @Override
    public List<FieldEffect> getActiveFieldEffects() {
        return Collections.unmodifiableList(this.fieldEffects);
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
        return currWave < getTotalWaves();
    }

    @Override
    public boolean hasGameEnded() {
        return !isPlayerAlive() ||
                (currWave == difficulty.getTotalWaves() && this.allCurrWaveEnemiesDead());
    }
}
