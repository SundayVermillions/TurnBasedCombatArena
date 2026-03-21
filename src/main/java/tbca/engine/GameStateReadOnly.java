package tbca.engine;

import tbca.combatant.Combatant;

import java.util.List;

public interface GameStateReadOnly {
    int currWave();

    GameDifficulty getDifficulty();

    Combatant getPlayer();

    List<Combatant> getCurrEnemies();

    boolean hasMoreWaves();

    boolean hasGameEnded();

    public boolean isPlayerAlive();
}
