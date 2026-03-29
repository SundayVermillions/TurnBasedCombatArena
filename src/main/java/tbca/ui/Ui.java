package tbca.ui;

<<<<<<< HEAD
import tbca.combatant.Combatant;
=======
import java.util.List;

>>>>>>> 37f2956fd594b7aa536b1971a3171e015a76703f
import tbca.combatant.player.playerclass.PlayerClass;
import tbca.effect.StatusEffect;
import tbca.engine.GameDifficulty;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.item.Item;
import tbca.item.ItemType;

public interface Ui {

    void displayMenu();
    GameDifficulty promptDifficulty();
    PlayerClass promptClassSelection();
    List<Item> promptItemSelection();

    ActionParameters getPlayerAction(GameStateReadOnly gameState);

    void showEndingScreen(GameStateReadOnly gameState);

    void displayTurnEnd(GameStateReadOnly gameState);

    void displayBasicAttack(GameStateReadOnly gameState, Combatant actor, List<Integer> targets, int dmg);
    void displayDefend(GameStateReadOnly gameState, Combatant actor);
    // Potion, SmokeBomb
    void displayUseItem(GameStateReadOnly gameState, Combatant actor, ItemType itemType);
//    void displaySpecialSkill (GameStateReadOnly gameState, Combatant actor,
//                              List<Integer> targets,
//                              List<Integer> dmg,
//                              List<StatusEffect> statuses);
    void displayTurnStart(GameStateReadOnly gameState);

}
