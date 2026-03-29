package tbca.ui;

import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.item.Item;
import tbca.ui.Battle.TurnDisplay;
import tbca.ui.Battle.GetPlayerAction;
import tbca.ui.Menu.ClassSelection;
import tbca.ui.Menu.Difficulty;
import tbca.ui.Battle.EndingScreen;
import tbca.ui.Menu.ItemSelection;

import java.util.List;
public class ConsoleUi implements Ui{
    ClassSelection classSelection;
    ItemSelection itemSelection;
    Difficulty difficulty;
    TurnDisplay turnDisplay;
    EndingScreen endingScreen;
    GetPlayerAction getPlayerAction;

    public ConsoleUi() {
        classSelection = new ClassSelection();
        itemSelection = new ItemSelection();
        difficulty = new Difficulty();
        turnDisplay = new TurnDisplay();
        endingScreen = new EndingScreen();
        getPlayerAction = new GetPlayerAction();
    }
    @Override
    public void displayMenu() {
        System.out.println("=========================================");
        System.out.println("       TURN-BASED COMBAT ARENA           ");
        System.out.println("=========================================\n");
    }

    @Override
    public GameDifficulty promptDifficulty() {
        return this.difficulty.promptDifficulty();
    }

    @Override
    public PlayerClass promptClassSelection() {
        return classSelection.classChoice();
    }

    @Override
    public List<Item> promptItemSelection() {
        return itemSelection.itemSelection();
    }

    @Override
    public ActionParameters getPlayerAction(GameStateReadOnly gameState) {
        return getPlayerAction.playerAction(gameState);
        }

    @Override
    public void showEndingScreen(GameStateReadOnly gameState) {
        endingScreen.showEndingScreen(gameState);
    }

    @Override
    public void displayTurnEnd(GameStateReadOnly gameState) {
        turnDisplay.displayTurnEnd(gameState);
    }

    @Override
    public void displayTurnStart(GameStateReadOnly gameState) {
        turnDisplay.displayTurnStart(gameState);
    }
}
