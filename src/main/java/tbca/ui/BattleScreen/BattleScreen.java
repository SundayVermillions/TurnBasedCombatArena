package tbca.ui.BattleScreen;

import tbca.domain.combatant.Combatant;
import tbca.domain.gamestate.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.results.ActionResults;

public class BattleScreen {
    private BattleScreenDisplayRenderer renderer;
    private BattleScreenInputHandler inputHandler;

    public BattleScreen() {
        this.renderer = new BattleScreenDisplayRenderer();
        this.inputHandler = new BattleScreenInputHandler();
    }

    public void displayTurnStart(GameStateReadOnly gameState) {
        BattleScreenDisplayRenderer.displayTurnStart(gameState);
    }

    public void displayTurnEnd(GameStateReadOnly gameState) {
        renderer.displayTurnEnd(gameState);
    }

    public void displayAction(GameStateReadOnly gameState, ActionResults actionResults) {
        renderer.displayAction(gameState, actionResults);
    }

    public void displayEnemyDefeated(GameStateReadOnly gameState, int enemyIndex) {
        renderer.displayEnemyDefeated(gameState, enemyIndex);
    }

    public void displayIncapacitated(Combatant combatant) {
        renderer.displayIncapacitated(combatant);
    }

    public ActionParameters playerAction(GameStateReadOnly gameState) {
        return inputHandler.playerAction(gameState);
    }
}