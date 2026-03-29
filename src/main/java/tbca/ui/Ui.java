package tbca.ui;

import java.util.List;

import tbca.combatant.Combatant;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.item.ItemType;

public interface Ui {

    void displayMenu();
    GameDifficulty promptDifficulty();
    PlayerClass promptClassSelection();
    List<ItemType> promptItemSelection();

    ActionParameters getPlayerAction(GameStateReadOnly gameState);

    void showEndingScreen(GameStateReadOnly gameState);

    void displayTurnEnd(GameStateReadOnly gameState);

    void displayTurnStart(GameStateReadOnly gameState);

    void displayBasicAttack(GameStateReadOnly gameState, Combatant actor, List<Integer> target, List<Integer> dmg);
    void displayDefend(GameStateReadOnly gameState, Combatant actor);
    void displayItem(GameStateReadOnly gameState, ItemType item);
}
