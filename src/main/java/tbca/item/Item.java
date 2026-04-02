package tbca.item;

import tbca.combatant.Combatant;
import tbca.engine.GameState;
import tbca.engine.action.results.SpecialSkillResults;

public interface Item {
    SpecialSkillResults use(Combatant user, GameState gameState, int targetIndex);
    String getName();
    ItemType getType();
}