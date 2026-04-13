package tbca.engine.action.results;

import tbca.domain.combatant.Combatant;
import tbca.engine.action.ActionType;

public record BasicAttackResults (Combatant actor,
                                  int targetEnemyIndex,
                                  int damage) implements ActionResults {

    @Override
    public ActionType actionType() {
        return ActionType.BASIC_ATTACK;
    }
}
