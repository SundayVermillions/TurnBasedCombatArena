package tbca.engine;

import java.util.List;

import tbca.combatant.Combatant;
import tbca.engine.difficulty.GameDifficulty;

public interface GameStateReadOnly {
    int currWave();

    GameDifficulty getDifficulty();

    Combatant getPlayer();

    List<Combatant> getCurrEnemies();

    boolean hasMoreWaves();

    boolean hasGameEnded();

    boolean isPlayerAlive();

    int getCurrTurn();

    int getTotalWaves();
}
