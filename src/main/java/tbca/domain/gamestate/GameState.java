package tbca.domain.gamestate;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.CombatantFactory;
import tbca.domain.combatant.player.playerclass.PlayerClass;
import tbca.domain.effect.fieldeffect.FieldEffect;
import tbca.domain.gamestate.difficulty.EnemyBlueprint;
import tbca.domain.gamestate.difficulty.GameDifficulty;
import tbca.domain.gamestate.difficulty.WaveBlueprint;
import tbca.domain.item.ItemType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState implements GameStateReadOnly {
    private final GameDifficulty difficulty;
    private final List<ItemType> startingItemSelection;
    private final PlayerClass startingPlayerClass;

    private Combatant player;
    private List<Combatant> currEnemies = new ArrayList<>();
    private List<FieldEffect> fieldEffects = new ArrayList<>();

    private int currWave = 0; // 1-indexed, 0 signifies no wave started
    private int currTurn = 0;

    public GameState(PlayerClass playerClass, List<ItemType> items, GameDifficulty difficulty) {
        this.startingItemSelection = items;
        this.difficulty = difficulty;
        this.startingPlayerClass = playerClass;
        CombatantFactory.resetCounters();
        this.player = CombatantFactory.createPlayer(playerClass, items);
    }

    public GameState(GameState gameState) {
        this(gameState.startingPlayerClass,
                gameState.startingItemSelection,
                gameState.difficulty);
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
        for (int i = fieldEffects.size() - 1; i >= 0; i--) {
            FieldEffect fieldEffect = fieldEffects.get(i);
            fieldEffect.tick(this);
            if (fieldEffect.isExpired()) {
                fieldEffect.removeEffect(getAllCombatants());
                fieldEffects.remove(i);
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
