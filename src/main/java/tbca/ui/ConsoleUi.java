package tbca.ui;

import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.item.ItemType;
import tbca.ui.BattleDisplay.DisplayOnly;
import tbca.ui.BattleProcedure.Selection;

import java.util.List;
public class ConsoleUi implements Ui{
    Selection selection;
    DisplayOnly displayOnly;

    public ConsoleUi() {
        selection = new Selection();
        displayOnly = new DisplayOnly();
    }
    @Override
    public void displayMenu() {
        this.displayOnly.displayMenu();
    }

    @Override
    public GameDifficulty promptDifficulty() {
        return this.selection.promptDifficulty();
    }

    @Override
    public PlayerClass promptClassSelection() {
        return selection.classChoice();
    }

    @Override
    public List<ItemType> promptItemSelection() {
        return selection.itemSelection();
    }

    @Override
    public ActionParameters getPlayerAction(GameStateReadOnly gameState) {
        return selection.playerAction(gameState);
        }

    @Override
    public void showEndingScreen(GameStateReadOnly gameState) {
        displayOnly.showEndingScreen(gameState);
    }

    @Override
    public void displayTurnEnd(GameStateReadOnly gameState) {
        displayOnly.displayTurnEnd(gameState);
    }

    @Override
    public void displayTurnStart(GameStateReadOnly gameState) {
        displayOnly.displayTurnStart(gameState);
    }

    @Override
    public void displayBasicAttack() {

    }
}
