package tbca.engine.action;

import tbca.combatant.Combatant;
import tbca.effect.DefendEffect;
import tbca.engine.GameState;
import tbca.engine.action.parameters.DefendParameters;
import tbca.engine.action.results.ActionResults;
import tbca.engine.action.results.DefendResults;

public class DefendAction extends Action {
    private final Combatant actor;

    public DefendAction(DefendParameters actionParameters) {
        actor = actionParameters.actor();
    }

    @Override
    public ActionType getType() {
        return ActionType.DEFEND;
    }

    @Override
    public ActionResults execute(GameState gameState) {
        actor.addStatusEffect(new DefendEffect());
        return new DefendResults(actor);
    }
}
