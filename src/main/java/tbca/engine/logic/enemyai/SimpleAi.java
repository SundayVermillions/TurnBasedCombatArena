package tbca.engine.logic.enemyai;

import tbca.combatant.Combatant;
import tbca.engine.GameStateReadOnly;
import tbca.engine.action.parameters.ActionParameters;
import tbca.engine.action.parameters.BasicAttackParameters;

public class SimpleAi implements AiController {
    @Override
    public ActionParameters decide(Combatant npc, GameStateReadOnly gameState) {
        return new BasicAttackParameters(npc);
    }
}
