package tbca.engine.action;

import tbca.combatant.Combatant;
import tbca.engine.GameState;

import static tbca.engine.action.ActionType.BASIC_ATTACK;
import tbca.engine.action.parameters.BasicAttackParameters;
import tbca.ui.Ui;

public class BasicAttackAction extends Action {
    private Combatant actor;


    public BasicAttackAction(BasicAttackParameters actionParameters) {
        this.actor = actionParameters.actor();
    }
    @Override
    public ActionType getType() {
        return BASIC_ATTACK;
    }

    @Override
    public void execute(Ui ui, GameState gameState) {
        //ui.displayBasicAttack((GameStateReadOnly)  gameState, actor, targets, dmg);
        //ui.displayDefend()
    }
}
