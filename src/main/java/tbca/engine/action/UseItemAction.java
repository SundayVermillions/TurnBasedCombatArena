package tbca.engine.action;

import tbca.engine.GameState;
import tbca.engine.action.parameters.ActionParameters;
import tbca.ui.Ui;

public class UseItemAction implements Action {
    public UseItemAction(ActionParameters actionParameters) {

    }
    @Override
    public ActionType getType() {
        return ActionType.USE_ITEM;
    }

    @Override
    public void execute(Ui ui, GameState gameState) {

    }
}
