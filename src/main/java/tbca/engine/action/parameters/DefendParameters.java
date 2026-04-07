package tbca.engine.action.parameters;

import tbca.combatant.Combatant;
import tbca.engine.action.Action;
import tbca.engine.action.ActionType;
import tbca.engine.action.DefendAction;

public record DefendParameters(Combatant actor) implements ActionParameters {
    @Override
    public ActionType actionType() {
        return ActionType.DEFEND;
    }

    @Override
    public Action createAction() {
        return new DefendAction(this);
    }
}
