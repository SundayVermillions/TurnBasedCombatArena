package tbca.engine;

import java.util.List;

import tbca.combatant.Combatant;
import tbca.combatant.CombatantFactory;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.action.Action;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.parameters.BasicAttackParameters;
import tbca.engine.action.results.ActionResults;
import tbca.engine.difficulty.GameDifficulty;
import tbca.engine.logic.enemyai.AiController;
import tbca.engine.logic.turnorder.SpeedTurnOrderStrategy;
import tbca.engine.logic.turnorder.TurnOrderStrategy;
import tbca.item.ItemType;
import tbca.ui.ConsoleUi;
import tbca.ui.Ui;

public class Game {
    private static Game gameInstance;
    private Ui ui;
    private GameState gameState;
    private TurnOrderStrategy turnOrderStrategy;

    private Game() {
        this.ui = new ConsoleUi();
        this.turnOrderStrategy = new SpeedTurnOrderStrategy();
    }

    public static Game getGameInstance() {
        if (gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    public void start() {
        this.initialize();

        while (!gameState.hasGameEnded()) {
            gameState.spawnNextWave();
            runWave(gameState);
        }

        this.ui.showEndingScreen(this.gameState); // either victory or loss
    }

    private void initialize() {
        this.ui.displayMenu();
        GameDifficulty selectedDifficulty = ui.promptDifficulty();
        PlayerClass playerClass = ui.promptClassSelection();
        List<ItemType> items = ui.promptItemSelection();

        Combatant player = CombatantFactory.createPlayer(playerClass, items);
        this.gameState = new GameState(player, selectedDifficulty);
    }

    private void runWave(GameState gameState) {
        // continue wave while enemies in currWave are alive and game has not ended
        while (!gameState.allCurrWaveEnemiesDead() && !gameState.hasGameEnded()) {
            this.gameState.incrementTurn();
            this.ui.displayTurnStart((GameStateReadOnly) gameState);
            ActionParameters selection = this.ui.getPlayerAction((GameStateReadOnly) gameState);
            List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(gameState);

            for (Combatant combatant : turnOrder) { // after making player selection, commence turn
                if (gameState.allCurrWaveEnemiesDead() || gameState.hasGameEnded())
                    break; // break if all enemies in this wave is dead, or player dies
                if (!combatant.isAlive())
                    continue; // if enemy died before getting to move this turn, skip him
                if (!combatant.canAct())
                    // TODO: this.ui.displayIncapacitated(combatant);
                    continue; // if current actor can't move due to status, print message and skip him

                // if is player, go with selected action. else, enemies can only basic attack
                Action action = combatant.isPlayer() ? selection.createAction()
                                                        : AiController.get(combatant.getAiType())
                                                                      .decide(combatant, gameState)
                                                                      .createAction();

                ActionResults actionResults = action.execute(gameState);
                ui.displayActionResults(gameState, actionResults);
            }
            // tick all active effects for all live combatants
            for (Combatant combatant : turnOrder) {
                combatant.tickAll();
            }
            this.ui.displayTurnEnd(gameState);
        }
    }

}
