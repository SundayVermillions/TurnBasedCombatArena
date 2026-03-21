package tbca.engine;

import tbca.combatant.Combatant;
import tbca.combatant.CombatantFactory;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.action.Action;
import tbca.engine.action.ActionFactory;
import tbca.engine.action.ActionParameters;
import tbca.engine.action.ActionType;
import tbca.engine.turnorder.SpeedTurnOrderStrategy;
import tbca.engine.turnorder.TurnOrderStrategy;
import tbca.item.Item;
import tbca.ui.Ui;

import java.util.List;

public class Game {
    private static Game gameInstance;
    private Ui ui;
    private GameState gameState;
    private TurnOrderStrategy turnOrderStrategy;

    private Game() {
        //this.ui = new Ui();
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
            runWave(gameState);
        }

        this.ui.showEndingScreen(this.gameState); // either victory or loss
    }

    private void runWave(GameState gameState) {
        gameState.spawnNextWave();

        // continue wave until all enemies in current wave are dead, or player is dead
        while (!gameState.allCurrWaveEnemiesDead() || !gameState.hasGameEnded()) {
            this.ui.displayTurnStart((GameStateReadOnly) gameState);
            ActionParameters selection = this.ui.getPlayerAction((GameStateReadOnly) gameState);
            List<Combatant> turnOrder = turnOrderStrategy.determineTurnOrder(gameState);

            for (Combatant combatant : turnOrder) {
                if (!gameState.allCurrWaveEnemiesDead() || gameState.hasGameEnded())
                    break; // break if all enemies in this wave is dead, or player dies

                Action action;
                if (combatant.isPlayer()) {
                    action = ActionFactory.create(
                            new ActionParameters(selection.actionType(),
                                    combatant,
                                    selection.targetEnemyIndex(),
                                    selection.itemType()));
                } else {
                    // monsters can only do basic attacks
                    action = ActionFactory.create(
                            new ActionParameters(ActionType.BASIC_ATTACK,
                                    combatant,
                                    0,
                                    null)
                    );
                }
                action.execute(this.ui, gameState);
            }
        }

        this.ui.displayTurnEnd(gameState);
    }

    private void initialize() {
        this.ui.displayMenu();
        GameDifficulty selectedDifficulty = ui.promptDifficulty();
        PlayerClass playerClass = ui.promptClassSelection();
        List<Item> items = ui.promptItemSelection();

        Combatant player = CombatantFactory.createPlayer(playerClass, items);
        this.gameState = new GameState(player, selectedDifficulty);
    }

    public void reset() {
        Game.gameInstance = new Game();
    }
}
