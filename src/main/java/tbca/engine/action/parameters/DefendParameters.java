package tbca.engine.action.parameters;

import tbca.combatant.Combatant;
import tbca.engine.action.ActionType;

public record DefendParameters(Combatant actor) implements ActionParameters {
    @Override
    public ActionType actionType() {
        return ActionType.DEFEND;
    }
}
