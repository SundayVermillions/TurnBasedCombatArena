package tbca.engine.action;

import tbca.combatant.Combatant;
import tbca.engine.GameState;
import tbca.engine.action.results.ActionResults;
import tbca.ui.Ui;

public abstract class Action {
    private Combatant actor;

    public abstract ActionType getType();

    public abstract ActionResults execute(GameState gameState);

}
