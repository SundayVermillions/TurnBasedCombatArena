package tbca.engine;

import java.util.List;

import tbca.domain.combatant.Combatant;
import tbca.domain.combatant.player.playerclass.PlayerClass;
import tbca.domain.gamestate.GameState;
import tbca.domain.gamestate.GameStateReadOnly;
import tbca.engine.action.Action;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.results.ActionResults;
import tbca.domain.gamestate.difficulty.GameDifficulty;
import tbca.engine.logic.enemyai.AiController;
import tbca.engine.logic.turnorder.SpeedTurnOrderStrategy;
import tbca.engine.logic.turnorder.TurnOrderStrategy;
import tbca.domain.item.ItemType;
import tbca.ui.ConsoleUi;
import tbca.ui.EndingScreen.EndingScreenOptions;
import tbca.ui.Ui;

import static tbca.ui.EndingScreen.EndingScreenOptions.*;

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
        EndingScreenOptions endingScreenOptions = START_NEW;
        do {
            if (endingScreenOptions == START_NEW)
                this.initialize();
            else if (endingScreenOptions == REPLAY_SAME_SETTINGS)
                restartGameState(this.gameState);


            while (!gameState.hasGameEnded()) {
                gameState.spawnNextWave();
                runWave(gameState);
            }

            this.ui.showEndingScreen(this.gameState); // either victory or loss
            endingScreenOptions = this.ui.promptEndingScreenChoice();

        } while (endingScreenOptions != EXIT);
    }

    private void initialize() {
        this.ui.displayMenu();
        GameDifficulty selectedDifficulty = ui.promptDifficulty();
        PlayerClass playerClass = ui.promptClassSelection();
        List<ItemType> items = ui.promptItemSelection();

        this.gameState = new GameState(playerClass, items, selectedDifficulty);
    }

    private void restartGameState(GameState gameState) {
        this.gameState = new GameState(gameState);
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
                if (!combatant.canAct()) {
                    this.ui.displayIncapacitated(combatant);
                    combatant.tickAll();
                    continue; // if current actor can't move due to status, print message and skip him
                }

                // if is player, go with selected action. else, defer to AiController for decision
                Action action = combatant.isPlayer() ? selection.createAction()
                                                        : AiController.get(combatant.getAiType())
                                                                      .decide(combatant, gameState)
                                                                      .createAction();

                ActionResults actionResults = action.execute(gameState);
                ui.displayActionResults(gameState, actionResults);
                combatant.tickAll();
            }
            this.gameState.tickAllFieldEffects();
            this.ui.displayTurnStatus(gameState);
        }
    }

}
