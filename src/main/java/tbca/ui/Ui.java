package tbca.ui;

import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.item.Item;

import java.util.List;

public interface Ui {

    void displayMenu();
    GameDifficulty promptDifficulty();
    PlayerClass promptClassSelection();
    List<Item> promptItemSelection();

    ActionParameters getPlayerAction(GameStateReadOnly gameState);

    void showEndingScreen(GameStateReadOnly gameState);

    void displayTurnEnd(GameStateReadOnly gameState);

    void displayTurnStart(GameStateReadOnly gameState);

}
