package tbca.ui;

import java.util.List;
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.engine.difficulty.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.results.ActionResults;
import tbca.item.ItemType;

public interface Ui {

    void displayMenu();
    GameDifficulty promptDifficulty();
    PlayerClass promptClassSelection();
    List<ItemType> promptItemSelection();

    ActionParameters getPlayerAction(GameStateReadOnly gameState);

    void showEndingScreen(GameStateReadOnly gameState);

    void displayTurnEnd(GameStateReadOnly gameState);

    void displayActionResults(GameStateReadOnly gameState, ActionResults actionResults);
//    void displayBasicAttack(GameStateReadOnly gameState, Combatant actor, List<Integer> targets, List<Integer> dmg);
//    void displayDefend(GameStateReadOnly gameState, Combatant actor);
//    // Potion, SmokeBomb
//    void displayUseItem(GameStateReadOnly gameState, Combatant actor, ItemType itemType);
//    void displaySpecialSkill (GameStateReadOnly gameState, Combatant actor,
//                              List<Integer> targets,
//                              List<Integer> dmg,
//                              List<StatusEffect> statuses);
    void displayTurnStart(GameStateReadOnly gameState);
    void displayEnemyDefeated(GameStateReadOnly gameState,int enemyIndex);
    // TODO: void displayIncapacitated(Combatant combatant);
}
