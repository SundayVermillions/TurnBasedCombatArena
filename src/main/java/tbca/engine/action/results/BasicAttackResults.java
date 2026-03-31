package tbca.engine.action.results;

import tbca.combatant.Combatant;
import tbca.engine.action.ActionType;

public record BasicAttackResults (Combatant actor,
                                  int targetEnemyIndex,
                                  int damage) implements ActionResults {

    @Override
    public ActionType actionType() {
        return ActionType.BASIC_ATTACK;
    }
}
