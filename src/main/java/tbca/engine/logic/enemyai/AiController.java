package tbca.engine.logic.enemyai;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;

public interface AiController {
    ActionParameters decide(Combatant npc, GameStateReadOnly gameState);
}