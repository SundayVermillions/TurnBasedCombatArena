package tbca.engine.action;

import tbca.domain.combatant.Combatant;
import tbca.domain.effect.DefendEffect;
import tbca.domain.gamestate.GameState;
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
