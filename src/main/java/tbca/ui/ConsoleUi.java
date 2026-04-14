package tbca.ui;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.player.playerclass.PlayerClass;
import tbca.domain.gamestate.difficulty.GameDifficulty;
import tbca.domain.gamestate.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.results.ActionResults;
import tbca.domain.item.ItemType;
import tbca.ui.BattleScreen.BattleScreen;
import tbca.ui.EndingScreen.EndingScreen;
import tbca.ui.EndingScreen.EndingScreenOptions;
import tbca.ui.StartingScreen.StartingScreen;

import java.util.List;

public class ConsoleUi implements Ui {
    StartingScreen loadingScreen;
    BattleScreen gameplayScreen;
    EndingScreen completionScreen;

    public ConsoleUi() {
        loadingScreen = new StartingScreen();
        gameplayScreen = new BattleScreen();
        completionScreen = new EndingScreen();
    }

    @Override
    public void displayMenu() {
        this.loadingScreen.startingMenu();
    }

    @Override
    public GameDifficulty promptDifficulty() {
        return this.loadingScreen.promptDifficulty();
    }

    @Override
    public PlayerClass promptClassSelection() {
        return loadingScreen.classChoice();
    }

    @Override
    public List<ItemType> promptItemSelection() {
        return loadingScreen.itemSelection();
    }

    @Override
    public ActionParameters getPlayerAction(GameStateReadOnly gameState) {
        return gameplayScreen.playerAction(gameState);
    }

    @Override
    public void displayTurnStatus(GameStateReadOnly gameState) {
        gameplayScreen.displayTurnEnd(gameState);
    }

    @Override
    public void displayActionResults(GameStateReadOnly gameState, ActionResults actionResults) {
        gameplayScreen.displayAction(gameState, actionResults);
    }

    @Override
    public void displayTurnStart(GameStateReadOnly gameState) {
        gameplayScreen.displayTurnStart(gameState);
    }

    @Override
    public void displayEnemyDefeated(GameStateReadOnly gameState, int enemyIndex) {
        gameplayScreen.displayEnemyDefeated(gameState, enemyIndex);
    }

    @Override
    public void displayIncapacitated(Combatant combatant) {
        gameplayScreen.displayIncapacitated(combatant);
    }

    @Override
    public void showEndingScreen(GameStateReadOnly gameState) {
        completionScreen.showEndingScreen(gameState);
    }

    @Override
    public EndingScreenOptions promptEndingScreenChoice() {
        return completionScreen.promptEndingScreenChoice();
    }
}