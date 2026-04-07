package tbca.engine.action.parameters;

import tbca.combatant.Combatant;
import tbca.engine.action.Action;
import tbca.engine.action.ActionType;
import tbca.engine.action.BasicAttackAction;

public record BasicAttackParameters(Combatant actor,
                                    int targetEnemyIndex) implements ActionParameters {

    // overloaded constructor for when it is enemy's basic attack action (only one player to target)
    public BasicAttackParameters(Combatant actor) {
        this(actor, 0);
    }

    @Override
    public ActionType actionType() {
        return ActionType.BASIC_ATTACK;
    }

    @Override
    public Action createAction() {
        return new BasicAttackAction(this);
    }
}
