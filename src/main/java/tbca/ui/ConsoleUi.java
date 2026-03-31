package tbca.ui;

import tbca.combatant.Combatant;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.results.ActionResults;
import tbca.item.ItemType;

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
    public void displayActionResults(GameStateReadOnly gameState, ActionResults actionResults) {
        displayOnly.displayAction(gameState,actionResults);
    }
    @Override
    public void displayTurnStart(GameStateReadOnly gameState) {
        displayOnly.displayTurnStart(gameState);
    }

    @Override
    public void displayEnemyDefeated(GameStateReadOnly gameState,int enemyIndex) {
        displayOnly.displayEnemyDefeated(gameState,enemyIndex);
    }
 /*
    @Override
    public void displayBasicAttack(GameStateReadOnly gameState, Combatant actor, List<Integer> target, List<Integer> dmg) {
        displayOnly.displayBasicAttack(gameState,actor,target,dmg);
    }
    @Override
    public void displayDefend(GameStateReadOnly gameState, Combatant actor) {
        displayOnly.displayDefend(gameState,actor);
    }

    @Override
    public void displayUseItem(GameStateReadOnly gameState,Combatant actor, ItemType item) {
        displayOnly.displayItem(gameState,actor,item);
    }

 */
}
