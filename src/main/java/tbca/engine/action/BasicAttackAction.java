package tbca.engine.action;

import tbca.engine.GameState;
import static tbca.engine.action.ActionType.BASIC_ATTACK;
import tbca.engine.action.parameters.BasicAttackParameters;
import tbca.ui.Ui;

public class BasicAttackAction implements Action {


    public BasicAttackAction(BasicAttackParameters actionParameters) {

    }
    @Override
    public ActionType getType() {
        return BASIC_ATTACK;
    }

    @Override
    public void execute(Ui ui, GameState gameState) {

    }
}
