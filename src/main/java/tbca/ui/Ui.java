package tbca.ui;

import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.ActionParameters;
import tbca.item.Item;

import java.util.List;

public interface Ui {

    public void displayMenu();
    public GameDifficulty promptDifficulty();
    public PlayerClass promptClassSelection();
    public List<Item> promptItemSelection();

    public void displayTurnStart(GameStateReadOnly gameStateReadOnly);

    public ActionParameters getPlayerAction(GameStateReadOnly gameState);

    public void showEndingScreen(GameStateReadOnly gameState);
}
