package tbca.domain.gamestate;

import java.util.List;

import tbca.domain.combatant.Combatant;
import tbca.domain.effect.FieldEffect;
import tbca.domain.gamestate.difficulty.GameDifficulty;

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

    int getNumOfRemainingEnemies();

    List<FieldEffect> getActiveFieldEffects();
}
