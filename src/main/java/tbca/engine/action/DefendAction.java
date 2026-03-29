package tbca.engine.action;

import tbca.engine.GameState;
import tbca.engine.action.parameters.DefendParameters;
import tbca.ui.Ui;

public class DefendAction extends Action {

    public DefendAction(DefendParameters actionParameters) {

    }

    @Override
    public ActionType getType() {
        return ActionType.DEFEND;
    }

    @Override
    public void execute(Ui ui, GameState gameState) {

    }
}
