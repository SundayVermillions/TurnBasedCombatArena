package tbca.engine.action;

import tbca.domain.combatant.Combatant;
import tbca.domain.gamestate.GameState;
import tbca.engine.action.results.ActionResults;

public abstract class Action {
    private Combatant actor;

    public abstract ActionType getType();

    public abstract ActionResults execute(GameState gameState);

}
