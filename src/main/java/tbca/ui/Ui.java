package tbca.ui;

import java.util.List;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.player.playerclass.PlayerClass;
import tbca.domain.gamestate.difficulty.GameDifficulty;
import tbca.domain.gamestate.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.results.ActionResults;
import tbca.domain.item.ItemType;
import tbca.ui.EndingScreen.EndingScreenOptions;

public interface Ui {

    void displayMenu();
    GameDifficulty promptDifficulty();
    PlayerClass promptClassSelection();
    List<ItemType> promptItemSelection();

    ActionParameters getPlayerAction(GameStateReadOnly gameState);
    void showEndingScreen(GameStateReadOnly gameState);
    EndingScreenOptions promptEndingScreenChoice();

    void displayTurnStatus(GameStateReadOnly gameState);

    void displayActionResults(GameStateReadOnly gameState, ActionResults actionResults);

    void displayTurnStart(GameStateReadOnly gameState);
    void displayEnemyDefeated(GameStateReadOnly gameState,int enemyIndex);
    void displayIncapacitated(Combatant combatant);
}
