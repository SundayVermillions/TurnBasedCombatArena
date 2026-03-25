package tbca.engine.action;

import tbca.engine.GameState;
import tbca.engine.action.parameters.ActionParameters;
import tbca.ui.Ui;

public class DefendAction implements Action {

    public DefendAction(ActionParameters actionParameters) {

    }

    @Override
    public ActionType getType() {
        return ActionType.DEFEND;
    }

    @Override
    public void execute(Ui ui, GameState gameState) {

    }
}
