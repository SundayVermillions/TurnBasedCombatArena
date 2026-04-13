package tbca.engine.action.results;

import tbca.domain.combatant.Combatant;
import tbca.engine.action.ActionType;

public record DefendResults(Combatant actor) implements ActionResults {
    @Override
    public ActionType actionType() {
        return ActionType.DEFEND;
    }
}
